package dados;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import informacao.Messagem;
import java.sql.*;

/**
 * Classe Responsavel pela manipulação da estrutura do Banco de Dados
 *
 * @author Carlos Eduardo dos Santos Figueiredo.
 *
 * @version 1.00
 * @since 01/05/2019
 */
public class DataBase extends ModuloConector {

    public static void main(String[] args) {
        criarDataBase("teste2");
        deletarDataBase("teste");
        System.out.println(verificarNaoExisterDataBase("teste2"));
        //System.out.println(HaCampoVazio(null, null));
    }
    //--------------------Atributos de manipulação ao Banco de Dados------------//
    private static Connection conexao;
    private static Statement stmt;
    private static ResultSet rs;
    private static ResultSetMetaData rsmd;
    private static PreparedStatement pst;
   
    //-------------------Metodo de acesso ao Banco de dados----------------------//
    private static void dados() {
        conexao = getConecction1();
    }
    //-------------------Metodo de Manipulação de Banco de dados----------------//

    /**
     * OK Este metodo faz a criação do banco de dados conforme o parametro
     * dataBase
     *
     * @author Carlos Eduardo dos Santos Figueiredo.
     *
     * @version 1.00
     * @since 01/05/2019
     *
     * @param dataBase Setar uma informação de valor String do nome banco de
     * dados
     */
    public static void criarDataBase(String dataBase) {
        if (NaoHaCampoVazio(null, dataBase)) {
            try {
                dados();
                String sql = "create database if not exists " + dataBase;
                stmt = conexao.createStatement();
                int criada = stmt.executeUpdate(sql);
                if (criada > 0) {
                    Messagem.chamarTela(Messagem.CRIADO("Banco " + dataBase));
                }
            } catch (SQLException e) {
                Messagem.chamarTela(e);
            } finally {
                ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
            }
        }
    }

    /**
     * OK Este metodo faz a exclução do banco de dados e de todos os dados
     * conforme o parametro dataBase
     *
     * @author Carlos Eduardo dos Santos Figueiredo.
     *
     * @version 1.00
     * @since 01/05/2019
     *
     * @param dataBase Setar uma informação de valor String do nome banco de
     * dados
     */
    public static void deletarDataBase(String dataBase) {
        if (NaoHaCampoVazio(null, dataBase)) {
            try {
                dados();
                String sql = "drop database if exists " + dataBase;
                stmt = conexao.createStatement();
                int deletada = stmt.executeUpdate(sql);
                System.out.println(deletada);
                if (deletada > 0) {
                    Messagem.chamarTela(Messagem.EXCLUIDO("Banco de Dado " + dataBase));
                }
                stmt = conexao.createStatement();
            } catch (SQLException e) {
                Messagem.chamarTela(e);
            } finally {
                ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
            }
        }
    }

    //------------------Metodo de Verificação ao Banco de dados-----------------//
    /**
     * OK Este método verificar se nao exister determinado banco de dados
     * através do parâmetro
     *
     * @param dataBase Setar uma informação de valor String do nome banco de
     * dados
     * @return retornar uma informação de vslor booleana se for TRUE : exister o
     * banco de dados ou ser for FALSE: nao exister o banco de dados
     */
    public static boolean verificarExisterDataBase(String dataBase) {
        boolean exister = false;
        if (NaoHaCampoVazio(null, dataBase)) {
            String sql = "show databases like ?";
            try {
                dados();
                pst = conexao.prepareStatement(sql);
                pst.setString(1, dataBase + "%");
                rs = pst.executeQuery();
                exister = rs.next();
            } catch (SQLException se) {
                Messagem.chamarTela(se);
            } finally {
                ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
            }
        }
        return exister;
    }

    /**
     * OK Este método verificar se nao exister determinado banco de dados
     * através do parâmetro
     *
     * @param dataBase Setar uma informação de valor String do nome banco de
     * dados
     * @return retornar uma informação de vslor booleana se for TRUE : nao
     * exister o banco de dados ou ser for FALSE: exister o banco de dados
     */
    public static boolean verificarNaoExisterDataBase(String dataBase) {
        return !verificarExisterDataBase(dataBase);
    }

    //-------------------Metodo de Validação de Parametro-----------------------//
    /**
     * Este metodo Retornar uma informação de valor booleanda se for TRUE: ha
     * campo vazio ; ou se for FALSE: Não ha campo vazio
     *
     * @param caminhoArquivo Setar uma informação de valor String do Caminho do
     * arquivo que deseja ser executado
     * @param dataBase Setar uma informação de valor String do nome banco de
     * dados
     * @return Retornar uma informação de valor booleanda se ha campo vazio
     */
    public static boolean HaCampoVazio(String caminhoArquivo, String dataBase) {
        boolean campo = true;
        if (caminhoArquivo != null && dataBase != null) {
            campo = caminhoArquivo.isEmpty() || dataBase.isEmpty();
        } else if (caminhoArquivo != null) {
            campo = caminhoArquivo.isEmpty();
        } else if (dataBase != null) {
            campo = dataBase.isEmpty();
        } else {
            campo = !campo;
        }
        if (campo) {
            Messagem.chamarTela(Messagem.VAZIO(CampoVazio(caminhoArquivo, dataBase)));
        }
        return campo;
    }

    /**
     * Este metodo Retornar uma informação de valor booleanda se for TRUE: nao
     * ha campo vazio ; ou se for FALSE: ha campo vazio
     *
     * @param caminhoArquivo Setar uma informação de valor String do Caminho do
     * arquivo que deseja ser executado
     * @param dataBase Setar uma informação de valor String do nome banco de
     * dados
     * @return Retornar uma informação de valor booleanda se não ha campo vazio
     */
    public static boolean NaoHaCampoVazio(String caminhoArquivo, String dataBase) {
        return !HaCampoVazio(caminhoArquivo, dataBase);
    }

    /**
     * este metodo Retornar uma informação um array de valor String dos campos
     * que tiver Vazia
     *
     * @param caminhoArquivo Setar uma informação de valor String do Caminho do
     * arquivo que deseja ser executado
     * @param Banco Setar uma informação de valor String do nome banco de dados
     * @return Retornar uma informação um array de valor String dos campos que
     * tiver Vazia
     * @since 23/12/2019
     */
    private static String[] CampoVazio(String CaminhoArquivo, String dataBase) {
        String[] vazio = new String[2];
        int i = 0;
        if (CaminhoArquivo != null && CaminhoArquivo.isEmpty()) {
            vazio[i++] = " Carminho  do Arquivo";
        }
        if (dataBase != null && dataBase.isEmpty()) {
            vazio[i++] = "Banco de Dados";
        }
        return vazio;
    }
}