package com.goddrinksjava.prep;

import com.goddrinksjava.prep.model.bean.database.Candidato;
import com.goddrinksjava.prep.model.bean.database.Cargo;
import com.goddrinksjava.prep.model.bean.database.Partido;
import com.goddrinksjava.prep.model.bean.database.Votos;
import com.goddrinksjava.prep.model.bean.dto.WS.WSCandidatoDTO;
import com.goddrinksjava.prep.model.bean.dto.WS.WSCandidaturaDTO;
import com.goddrinksjava.prep.model.bean.dto.WS.WSCasillaDTO;
import com.goddrinksjava.prep.model.bean.dto.WS.WSPrepDTO;
import com.goddrinksjava.prep.model.dao.CandidatoDAO;
import com.goddrinksjava.prep.model.dao.CargoDAO;
import com.goddrinksjava.prep.model.dao.CasillaDAO;
import com.goddrinksjava.prep.model.dao.VotosDAO;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class PrepDTOService {
    @Inject
    private Event<WSPrepDTO> event;

    @Inject
    VotosDAO votosDAO;

    @Inject
    CargoDAO cargoDAO;

    @Inject
    CandidatoDAO candidatoDAO;

    @Inject
    CasillaDAO casillaDAO;

    public WSPrepDTO generatePrepDTO() throws SQLException {
        List<WSCasillaDTO> WSCasillaDTOList = new ArrayList<>();

        List<Cargo> cargoList = cargoDAO.findAll();
        List<Candidato> candidatoList = candidatoDAO.findAll();
        List<Integer> idsCasilla = casillaDAO.findAllIds();
        Map<Cargo, List<Candidato>> candidatosByCargo = new HashMap<>();
        Map<Candidato, List<String>> candidatoPartidos = new HashMap<>();

        for (Cargo cargo : cargoList) {
            candidatosByCargo.put(
                    cargo,
                    candidatoList.stream()
                            .filter(candidato -> candidato.getFkCargo().equals(cargo.getId()))
                            .collect(Collectors.toList())
            );
        }

        for (Candidato candidato : candidatoList) {
            candidatoPartidos.put(
                    candidato,
                    candidatoDAO.getPartidosById(candidato.getId())
            );
        }


        for (Integer idCasilla : idsCasilla) {
            List<Votos> votosList = votosDAO.getByCasilla(idCasilla);
            List<WSCandidaturaDTO> WSCandidaturaDTOList = new ArrayList<>();

            for (Map.Entry<Cargo, List<Candidato>> entry : candidatosByCargo.entrySet()) {
                List<WSCandidatoDTO> WSCandidatoDTOList = new ArrayList<>();

                for (Candidato candidato : entry.getValue()) {
                    WSCandidatoDTOList.add(
                            WSCandidatoDTO.builder()
                                    .votos(
                                            votosList.stream()
                                                    .filter(
                                                            votos -> votos.getFkCandidato().equals(candidato.getId())
                                                    )
                                                    .findFirst().get().getCantidad()
                                    )
                                    .partidos(
                                            candidatoPartidos.get(candidato)
                                    )
                                    .build()
                    );
                }

                WSCandidaturaDTOList.add(
                        WSCandidaturaDTO.builder()
                                .nombre(entry.getKey().getNombre())
                                .candidatos(WSCandidatoDTOList)
                                .build()
                );
            }
            WSCasillaDTOList.add(
                    WSCasillaDTO.builder()
                            .id(idCasilla)
                            .candidaturas(WSCandidaturaDTOList)
                            .build()
            );
        }

        return new WSPrepDTO(WSCasillaDTOList);
    }

    @Asynchronous
    public void fireEvent() throws SQLException {
        event.fire(generatePrepDTO());
    }
}
