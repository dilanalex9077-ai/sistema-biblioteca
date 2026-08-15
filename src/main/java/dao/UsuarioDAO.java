package dao;

import conexion.ConexionBD;
import modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de realizar las operaciones CRUD de usuarios.
 */
public class UsuarioDAO {

    public boolean guardar(Usuario usuario) {

        String sql = """
                INSERT INTO usuarios
                (nombre, apellido, telefono, correo, activo)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection conexion = ConexionBD.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getCorreo());
            ps.setBoolean(5, usuario.isActivo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    public boolean actualizar(Usuario usuario) {

        String sql = """
                UPDATE usuarios
                SET nombre = ?,
                    apellido = ?,
                    telefono = ?,
                    correo = ?,
                    activo = ?
                WHERE id = ?
                """;

        try (
                Connection conexion = ConexionBD.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getCorreo());
            ps.setBoolean(5, usuario.isActivo());
            ps.setInt(6, usuario.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    public boolean eliminar(int id) {

        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (
                Connection conexion = ConexionBD.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    public List<Usuario> listar() {

        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY apellido, nombre";

        try (
                Connection conexion = ConexionBD.getConexion();
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(sql)
        ) {

            while (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setActivo(rs.getBoolean("activo"));

                lista.add(usuario);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return lista;

    }

}
