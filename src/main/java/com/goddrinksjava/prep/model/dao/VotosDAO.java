package com.goddrinksjava.prep.model.dao;

import com.goddrinksjava.prep.model.pojo.database.Candidato;
import com.goddrinksjava.prep.model.pojo.database.Cargo;
import com.goddrinksjava.prep.model.pojo.database.Votos;
import com.goddrinksjava.prep.model.pojo.dto.CandidatoDTO;
import com.goddrinksjava.prep.model.pojo.dto.CandidaturaDTO;
import com.goddrinksjava.prep.model.pojo.dto.CasillaDTO;
import com.goddrinksjava.prep.model.pojo.dto.PrepDTO;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class VotosDAO {
    @Resource(lookup = "java:global/jdbc/prep")
    private DataSource dataSource;

    @Inject
    private CandidatoDAO candidatoDAO;

    @Inject
    private CargoDAO cargoDAO;

    @Inject
    private Event<PrepDTO> event;

    public List<Votos> getAll() throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select * from VOTOS"
                )
        ) {
            return map(stmt.executeQuery());
        }
    }

    public List<Integer> getDistinctFkCasillas() throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select distinct FK_CASILLA from VOTOS"
                )
        ) {
            List<Integer> fkCasillas = new ArrayList<>();
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                fkCasillas.add(
                        resultSet.getInt(1)
                );
            }
            return fkCasillas;
        }
    }

    public List<Votos> getByCasilla(Integer fk_casilla) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select * from VOTOS where FK_CASILLA = ?"
                )
        ) {
            stmt.setInt(1, fk_casilla);
            return map(stmt.executeQuery());
        }
    }

    public void insert(Votos votos) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "insert into VOTOS (CANTIDAD, FK_CASILLA, FK_CANDIDATO) VALUES ( ?, ?, ? )"
                )
        ) {
            stmt.setInt(1, votos.getCantidad());
            stmt.setInt(2, votos.getFkCasilla());
            stmt.setInt(3, votos.getFkCandidato());
            stmt.executeUpdate();
            fireEvent();
        }
    }

    public void update(List<Votos> votosList) throws SQLException {
        if (votosList.size() == 0) {
            return;
        }

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "update VOTOS set CANTIDAD=? where FK_CASILLA=? and FK_CANDIDATO=?"
                )
        ) {
            for (Votos votos : votosList) {
                System.out.println(votos);
                stmt.setInt(1, votos.getCantidad());
                stmt.setInt(2, votos.getFkCasilla());
                stmt.setInt(3, votos.getFkCandidato());
                stmt.executeUpdate();
            }

            fireEvent();
        }
    }

    public List<Votos> map(ResultSet resultSet) throws SQLException {

        List<Votos> votosList = new ArrayList<>();

        while (resultSet.next()) {
            votosList.add(
                    Votos.builder()
                            .cantidad(resultSet.getInt("cantidad"))
                            .fkCasilla(resultSet.getInt("fk_casilla"))
                            .fkCandidato(resultSet.getInt("fk_candidato"))
                            .build()
            );
        }

        return votosList;
    }

    public PrepDTO generatePrepDTO() throws SQLException {
        List<CasillaDTO> casillaDTOList = new ArrayList<>();

        List<Integer> fkCasillas = getDistinctFkCasillas();
        List<Cargo> cargos = cargoDAO.getAll();

        for (Integer fkCasilla : fkCasillas) {
            List<Votos> votosList = getByCasilla(fkCasilla);

            List<CandidaturaDTO> candidaturaDTOList = new ArrayList<>();

            for (Cargo cargo : cargos) {
                List<CandidatoDTO> candidatoDTOList = new ArrayList<>();

                for (Candidato candidato : candidatoDAO.getByCargo(cargo.getId())) {
                    candidatoDTOList.add(
                            CandidatoDTO.builder()
                                    .nombre(candidato.getNombre())
                                    .votos(
                                            votosList.stream()
                                                    .filter(
                                                            votos -> votos.getFkCandidato().equals(candidato.getId())
                                                    )
                                                    .findFirst().get().getCantidad()
                                    )
                                    .partidos(
                                            candidatoDAO.getPartidosById(candidato.getId())
                                    )
                                    .build()
                    );
                }

                candidaturaDTOList.add(
                        CandidaturaDTO.builder()
                                .nombre(cargo.getNombre())
                                .candidatos(candidatoDTOList)
                                .build()
                );
            }


            casillaDTOList.add(
                    CasillaDTO.builder()
                            .id(fkCasilla)
                            .candidaturas(candidaturaDTOList)
                            .build()
            );

        }

        casillaDTOList.forEach(System.out::println);

        return new PrepDTO(casillaDTOList);
    }

    public void fireEvent() throws SQLException {
        event.fire(generatePrepDTO());
    }

}

