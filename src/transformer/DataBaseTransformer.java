package transformer;

import dados.ModuloConector;
import informacao.Messagem;
import java.io.*;
import java.sql.*;
import dados.DataBase;

/**
 * Classe Responsavel pela Transformação da estrutura do Banco de Dados em
 *
 * @author Carlos Eduardo dos Santos Figueiredo.
 *
 * @version 2.00
 * @since 24/07/2024
 */
public class DataBaseTransformer {

    //--------------------Atributos de manipulação ao Banco de Dados------------//
    private static Connection conexao;
    private static Statement stmt;
    private static ResultSet rs;
    private static ResultSetMetaData rsmd;
    private static PreparedStatement pst;
    //--------------------Atributos de manipulação de Arquivo-------------------//
    private static BufferedReader bufferArquivo;
    private static File arg;
    private static FileReader leitorArquivo;
    private static FileWriter escrita;
    private static String linha = new String();
    private static PrintWriter gravarArquivo;

    //------------------Metodo de Manipulação de Arquivo------------------------//
    /**
     * Testando Este Metodo faz a importação de arquivo do tipo sql, com a
     * localizaçao o arquivo atravez do parametro
     *
     * @param caminhoArquivo Setar uma informação de valor String do Caminho do
     * arquivo que deseja ser executado
     * @param Banco Setar uma informação de valor String do nome banco de dados
     * @since 23/12/2019
     * @since 24/07/2024
     * Transpor este metodo da classe DataBase para a classe DataBaseTransformer
     */
    public static void importarBackupdataBaseSQL(String caminhoArquivo, String Banco) {

        if (DataBase.NaoHaCampoVazio(caminhoArquivo, Banco)) {
            arg = new File(caminhoArquivo);
            String Conteudo = "";
            int indexLinha = 0;
            if (arg.exists()) {
                try {
                    // pega a localizaçao do arquivo
                    leitorArquivo = new FileReader(caminhoArquivo);
                    //ler a linha do arquivo
                    bufferArquivo = new BufferedReader(leitorArquivo);
                    while (true) {
                        linha = bufferArquivo.readLine();
                        if (linha == null) {
                            System.out.println("1");
                            if (indexLinha == 0) {
                                Messagem.chamarTela("O arquivo se encontra Vazio  ou não existe");
                            }
                            break;
                        } else {
                            System.out.println("2");
                            if (!linha.startsWith("*/") || !linha.startsWith("--") || !linha.isEmpty() || !linha.startsWith("*") || !linha.startsWith("/*")) {
                                System.out.println("21");
                                // este e o incio do Bloco separa o comentario  da instrução sql
                                System.out.println(linha);
                                Conteudo += linha;
                                if (Conteudo.endsWith(";")) {
                                    // este Bloco executar instrução sql
                                    try {
                                        if (indexLinha == 0 || Banco == null) {
                                            conexao = ModuloConector.getConecction1();
                                        } else {
                                            conexao = ModuloConector.getConecction(Banco);
                                        }
                                        stmt = conexao.createStatement();
                                        int adicionar = stmt.executeUpdate(Conteudo);
                                        if (adicionar > 0) {
                                            // Messagem.chamarTela(Conteudo);
                                        }
                                        System.err.println(Conteudo);
                                    } catch (SQLException e) {
                                        //e.toString().
                                        System.err.println(e);
                                        Messagem.chamarTela(e);
                                    } finally {
                                        Conteudo = "";
                                        ModuloConector.fecharConexao(conexao, rs, rsmd, pst, stmt);
                                        indexLinha++;
                                    }
                                    // este Bloco executar instrução sql
                                } else {
                                    //  else  do  comentario MYSqul
                                }
                                // este e o FIM do Bloco separa o comentario  da instrução sql
                            } else {
                                //  else  do  comentario MYSqul
                            }
                        }
                    }
                } catch (FileNotFoundException FNFE) {
                    Messagem.chamarTela(FNFE);
                } catch (IOException IOE) {
                    Messagem.chamarTela(IOE);
                } finally {
                    arg.deleteOnExit();
                    linha = null;
                }
            } else {
                Messagem.chamarTela("O arquivo se encontra Vazio ou não existe");
            }
        }
    }

    /**
     * Este metodo faz a exportação de arquivo do tipo sql, com a localização o
     * arquivo atravez do paramentro 1.faz uma verificação dos parametros.
     *
     * @param caminhoArquivo Setar uma informação de valor String do Caminho do
     * arquivo que deseja ser executado
     * @param Banco Setar uma informação de valor String do nome banco de dados
     * @since 01/05/2019
     * Transpor este metodo da classe DataBase para a classe DataBaseTransformer
     */
    public static void exportarBackupdataBaseSQL(String caminhoArquivo, String Banco) {
        if (DataBase.NaoHaCampoVazio(caminhoArquivo, Banco)) {
            try {
                // A linha abaixo procurar o arquivo.
                escrita = new FileWriter(caminhoArquivo);
                //https://www.youtube.com/watch?v=PS44nHjvtdo
                gravarArquivo = new PrintWriter(escrita);
                //Escreve no arquivo
                gravarArquivo.println(Banco);
                if (!gravarArquivo.checkError()) {
                    System.out.println("gravado com sucesso!!");
                }
                escrita.close();
            } catch (IOException ex) {
                Messagem.chamarTela("Exportação de Backup" + ex);
            } finally {
                gravarArquivo.close();
            }
        }
    }
}
