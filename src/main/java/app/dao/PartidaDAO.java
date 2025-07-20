package app.dao;

import app.DatabaseConnection;
import app.model.Partida;
import app.model.TRanking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import listas.ListaRankingOrdenada;

public class PartidaDAO {

    public void insertar(Partida p) throws SQLException {
        String sql = "INSERT INTO partida(id_jugador, puntos, en_progreso) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getIdJugador());
            stmt.setInt(2, p.getPuntos());
            stmt.setBoolean(3, p.isEnProgreso());
            stmt.executeUpdate();
        }
    }

    public List<Partida> obtenerTodas() throws SQLException {
        List<Partida> lista = new ArrayList<>();
        String sql = "SELECT * FROM partida";

        try (Connection conn = DatabaseConnection.getInstance(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Partida p = new Partida();
                p.setIdPartida(rs.getInt("id_partida"));
                p.setIdJugador(rs.getInt("id_jugador"));
                p.setPuntos(rs.getInt("puntos"));
                p.setEnProgreso(rs.getBoolean("en_progreso"));
                p.setFechaInicio(rs.getTimestamp("fecha_inicio").toLocalDateTime());
                Timestamp fin = rs.getTimestamp("fecha_fin");
                p.setFechaFin(fin != null ? fin.toLocalDateTime() : null);
                lista.add(p);
            }
        }
        return lista;
    }

    public List<TRanking> obtenerRankingMaximo() throws SQLException {
        List<TRanking> lista = new ArrayList<>();
        String sql = "SELECT j.nombre, p.puntos FROM partida p JOIN player j ON p.id_jugador = j.id_jugador;";

        try (Connection conn = DatabaseConnection.getInstance(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int puntos = rs.getInt("puntos");
                lista.add(new TRanking(nombre, puntos));
            }
        }

        return lista;
    }

    public void cargarDatosTabla(DefaultTableModel tableModel) throws SQLException {
        List<TRanking> listaDesdeBD = obtenerRankingMaximo();

        ListaRankingOrdenada listaOrdenada = new ListaRankingOrdenada();
        for (TRanking r : listaDesdeBD) {
            listaOrdenada.insertarOrdenado(r);
        }

        List<TRanking> listaFinal = listaOrdenada.aLista();

        tableModel.setRowCount(0);
        int index = 1;
        for (TRanking ranking : listaFinal) {
            tableModel.addRow(new Object[]{
                index,
                ranking.getNombre(),
                ranking.getPuntos()
            });
            index++;
        }
    }

    public Partida buscarPartidaPorJugador(int idJugador) throws SQLException {
        String sql = "SELECT * FROM partida WHERE id_jugador = ?";
        try (Connection conn = DatabaseConnection.getInstance(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idJugador);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Partida p = new Partida();
                    p.setIdPartida(rs.getInt("id_partida"));
                    p.setIdJugador(rs.getInt("id_jugador"));
                    p.setPuntos(rs.getInt("puntos"));
                    p.setEnProgreso(rs.getBoolean("en_progreso"));
                    p.setFechaInicio(rs.getTimestamp("fecha_inicio").toLocalDateTime());
                    Timestamp fin = rs.getTimestamp("fecha_fin");
                    p.setFechaFin(fin != null ? fin.toLocalDateTime() : null);
                    return p;
                }
            }
        }
        return null;
    }

    public void actualizarPartida(int idPartida, int puntos, boolean paused) throws SQLException {
        String sql = "UPDATE partida SET puntos = ?, en_progreso = ? WHERE id_partida = ?";
        try (Connection conn = DatabaseConnection.getInstance(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, puntos);
            stmt.setInt(2, paused ? 1 : 0);
            stmt.setInt(3, idPartida);
            stmt.executeUpdate();
        }
    }

}
