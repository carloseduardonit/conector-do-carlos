package dados;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import informacao.Messagem;
import java.io.IOException;
import java.sql.*;

/**
 * @version 1.2
 * @author Carlos Eduardo dos Santos Figueiredo
 * @see ModuloConector
 * @since 01/05/2019
 */
public class ModuloConector {

    /**
     * Nome do banco de dados
     */
    public static final String DATABASE = "teste";
    /**
     * Driver do banco de dados
     */
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    /**
     * A URL do banco de dados
     */
    public static final String URL = "jdbc:mysql://localhost:3306/";
    /**
     * A URL + Nome do banco de dados
     */
    public static final String URLD = URL + DATABASE;
    /**
     * Usuario do banco de dados
     */
    public static final String USER = "root";
    /**
     * Senha do banco de dados
     */
    public static final String PASS = "";
    //Variaveis de gerencimento do bano de dados
    /**
     *
     */
    private static Connection conexao = null;
    /**
     *
     */
    private static PreparedStatement pst = null;
    /**
     *
     */
    private static ResultSet rs = null;
    /**
     *
     */
    private static ResultSetMetaData rsmd = null;
    /**
     *
     */
    private static Statement stmt = null;
    private static int count = 0;

    public static void main(String[] args) {
        DataBase.criarDataBase("teste2");
        //ImportarSQL();
    }

    //--------------------------------------------------------------- -----------//
    /**
     * Este metodo faz a conexão com o banco de dados
     */
    public static void conector() {
        conexao = getConecction();
    }

    //--------------------------------------------------------------------------//
    /**
     * Este metodo faz a conexao com o banco de dados MYSQL utilizado as
     * variaveis finais
     *
     * @version 1.0
     * @since 01/05/2019
     * @return a conexao conexao com o banco de dado
     */
    public static java.sql.Connection getConecction() {
        return getConecction(DATABASE, USER, PASS);
    }

    /**
     * Este metodo faz a conexao com o banco de dados MYSQL utilizado as
     * variaveis finais
     *
     * @version 1.0
     * @param Banco Setar uma informação de valor String com o nome banco de
     * dados
     * @since 01/05/2019
     * @return a conexao conexao com o banco de dado
     */
    public static java.sql.Connection getConecction(String Banco) {
        return getConecction(Banco, USER, PASS);
    }

    /**
     * Este metodo faz a conexao com o banco de dados MYSQL utilizado as
     * variaveis finais
     *
     * @version 1.0
     *
     * @param Banco Setar uma informação de valor String com o nome banco de
     * dados
     * @param Usuario Setar uma informação de valor String com o nome do usuario
     * do Banco
     * @since 01/05/2019
     * @return a conexao conexao com o banco de dado
     */
    public static java.sql.Connection getConecction(String Banco, String Usuario) {
        return getConecction(Banco, Usuario, PASS);
    }

    /**
     * Este metodo faz a conexao com o banco de dados MYSQL utilizado as
     * variaveis finais
     *
     * @version 1.0
     * @param Banco Setar uma informação de valor String com o nome banco de
     * dados
     * @param Usuario Setar uma informação de valor String com o nome do usuario
     * do Banco
     * @param Senha Setar uma informação de valor String com a senha do banco de
     * dados
     * @since 01/05/2019
     * @return a conexao conexao com o banco de dado
     */
    public static java.sql.Connection getConecction(String Banco, String Usuario, String Senha) {
        return getConecction(DRIVER, Banco, Usuario, Senha);
    }

    /**
     * Este metodo faz a conexao com o banco de dados MYSQL utilizado as
     * variaveis finais
     *
     * @version 1.0
     * @param Drive
     * @param Banco Setar uma informação de valor String com o nome banco de
     * dados
     * @param Usuario Setar uma informação de valor String com o nome do usuario
     * do Banco
     * @param Senha Setar uma informação de valor String com a senha do banco de
     * dados
     * @since 01/05/2019
     * @return a conexao conexao com o banco de dado
     */
    public static java.sql.Connection getConecction(String Drive, String Banco, String Usuario, String Senha) {
        try {
            Class.forName(Drive);
            return conexao = DriverManager.getConnection(URL + Banco + "\\", Usuario, Senha);
        } // catch{ }
        catch (ClassNotFoundException cnfe) {
            Messagem.chamarTela("O Servidor ultra-passou o limite de Conexão " + cnfe);
            fecharConexao(conexao, rs, rsmd, pst, stmt);
        } catch (SQLException e) {
            if (getCount() == 0) {
                abrirAplicação();
                setCount(+1);
                Messagem.chamarTela(getCount() + " O banco de dados deve esta desligado : " + e);
            }

            fecharConexao(conexao, rs, rsmd, pst, stmt);
            getConecction(Banco, Usuario, Senha);
        }
        setCount(0);
        isConectado(conexao);
        return conexao;
    }

    /**
     * Este metodo faz a conexao com o banco de dados MYSQL utilizado as
     * variaveis finais
     *
     * @version 1.0
     * @since 01/05/2019
     * @return a conexao conexao com o banco de dado
     */
    public static java.sql.Connection getConecction1() {
        return getConecction1(USER, PASS);
    }

    /**
     * Este metodo faz a conexao com o banco de dados MYSQL utilizado as
     * variaveis finais
     *
     * @version 1.0
     * @param Usuario Setar uma informação de valor String com o nome do usuario
     * do Banco
     * @param Senha Setar uma informação de valor String com a senha do banco de
     * dados
     * @since 01/05/2019
     * @return a conexao conexao com o banco de dado
     */
    public static java.sql.Connection getConecction1(String Usuario, String Senha) {
        try {
            Class.forName(DRIVER);
            return conexao = DriverManager.getConnection(URL, Usuario, Senha);
        } // catch{ }
        catch (ClassNotFoundException cnfe) {
            Messagem.chamarTela("O Servidor ultra-passou o limite de Conexão " + cnfe);
            fecharConexao(conexao, rs, rsmd, pst, stmt);
        } catch (SQLException e) {
            if (getCount() == 0) {
                abrirAplicação();
                setCount(+1);
                Messagem.chamarTela(getCount() + " O banco de dados deve esta desligado 1 : " + e);
            }

            fecharConexao(conexao, rs, rsmd, pst, stmt);
            getConecction1(Usuario, Senha);
        }
        setCount(0);
        isConectado(conexao);
        return conexao;
    }

    //--------------------------------------------------------------------------//
    /**
     *
     * @param conexao
     * @return
     */
    public static boolean isConectado(Connection conexao) {
        boolean res = conexao != null;
        if (res) {
            System.out.println("Esta conextado");
        } else {
            System.out.println("nao Esta conextado");
        }
        return res;
    }

    //--------------------------------------------------------------------------//
    /**
     * Este Metodo executar uma aplicação atraves do caminho da aplicação pre-
     * definida.
     */
    public static void abrirAplicação() {
        String b = "C:\\xampp\\xampp-control.exe";
        abrirAplicação(b);

    }

    /**
     * Este Metodo executar uma aplicação atraves do caminho da aplicação
     * comforme o parametro
     *
     * @param caminhodaAplicação Setar uma informação de valor String do Caminho
     * da aplicação que deseja ser executado
     */
    public static void abrirAplicação(String caminhodaAplicação) {
        try {
            Runtime r;
            r = Runtime.getRuntime();
            r.exec(caminhodaAplicação);
        } catch (IOException e) {
            Messagem.chamarTela(e);
        }
    }

    //--------------------------------------------------------------------------//
    /**
     * Este Metodo faz o fechamento da conexao
     *
     * @version 1.0 Este Metodo faz o fechamento da conexao
     * @since 01/05/2019
     * @param con - Fecha a conexao do banco
     */
    public static void fecharConexao(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            Messagem.chamarTela(e);
        }
    }

    /**
     *
     * Este Metodo faz o fechamento da conexao e Resultado
     *
     * @since 01/05/2019
     * @param con - Fecha a conexao do banco
     * @param rs - Fecha o resultado do Banco
     */
    public static void fecharConexao(Connection con, ResultSet rs) {
        try {
            if (rs != null) {
                fecharConexao(con);
                rs.close();
            } else {
                fecharConexao(con);
            }
        } catch (SQLException e) {
            Messagem.chamarTela(e);
        }
    }

    /**
     *
     * Este Metodo faz o fechamento da conexao e Resultado
     *
     * @since 13/07/2019
     * @param rsmd fecha o resuldado de meta dado
     * @since 01/05/2019
     * @version 1.1
     * @param con - Fecha a conexao do banco
     * @param rs - Fecha o resultado do Banco
     */
    public static void fecharConexao(Connection con, ResultSet rs, ResultSetMetaData rsmd) {
        if (rsmd != null) {
            fecharConexao(con, rs);
        } else {
            fecharConexao(con, rs);
        }
    }

    /**
     * Este Metodo faz o fechamento da conexao, Resultado e Editação
     *
     * @since 13/07/2019
     * @param rsmd fecha o resuldado de meta dado
     * @since 01/05/2019
     * @param con - Fecha a conexao do banco
     * @param rs - Fecha o resultado do Banco
     * @param pst -Fecha a
     */
    public static void fecharConexao(Connection con, ResultSet rs, ResultSetMetaData rsmd, PreparedStatement pst) {
        try {
            if (pst != null) {
                fecharConexao(con, rs);
                pst.close();
            } else {
                fecharConexao(con, rs);
            }
        } catch (SQLException e) {
            Messagem.chamarTela(e);
        }
    }

    /**
     * Este Metodo faz o fechamento da conexao, Resultado e Editação
     *
     * @since 13/07/2019
     * @param rsmd fecha o resuldado de meta dado
     * @since 01/05/2019
     * @param con Fecha a conexao do banco
     * @param rs Fecha o resultado do Banco
     * @param pst Fecha a
     * @param stmt Fecha a
     */
    public static void fecharConexao(Connection con, ResultSet rs, ResultSetMetaData rsmd, PreparedStatement pst, Statement stmt) {
        try {
            if (stmt != null) {
                fecharConexao(con, rs, rsmd, pst);
                stmt.close();
            } else {
                fecharConexao(con, rs, rsmd, pst);
            }
        } catch (SQLException e) {
            Messagem.chamarTela(e);
        }
    }

    //--------------------------------------------------------------------------//
    // Inicio dos Sets e Gets
    private static int getCount() {
        return count;
    }

    private static void setCount(int count) {
        ModuloConector.count = count;
    }
}
