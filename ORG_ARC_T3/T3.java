import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class T3 {

  private static String path = "ORG_ARC_T3/Arquivo.txt";
  private static Scanner in = new Scanner(System.in);
  private static ArrayList<String> bin = new ArrayList<String>();
  private static ArrayList<String> listaDeEnderecos = new ArrayList<String>();
  private static String[] vet;

  public static void main(String[] args) throws IOException {
    startup();
    int opcao;

    do {
      System.out.println("\nQuantas enderecos voce quer?");
      int numDeEnderecos = in.nextInt();
      in.nextLine();
      while (numDeEnderecos <= 0) {
        System.out.println("Valor invalido");
        System.out.println("Quantas enderecos voce quer?");
        numDeEnderecos = in.nextInt();
      }

      boolean temVariosEnderecos = false;
      if (numDeEnderecos > 1) {
        temVariosEnderecos = true;
        for (int i = 0; i < numDeEnderecos; i++) {
          System.out.println("Informe o endereco numero " + (i + 1));
          String entradaTemp = in.nextLine();
          while (entradaTemp.length() != 16) {
            System.out.println("Porfavor digite um endereco com 16 bits");
            entradaTemp = in.nextLine();
          }
          listaDeEnderecos.add(entradaTemp);
        }
      }

      System.out.println("-------------------");
      System.out.println("| 1 - Operacao 1  |");
      System.out.println("| 2 - Operacao 2  |");
      System.out.println("| 3 - Operacao 3  |");
      System.out.println("| 4 - Operacao 4  |");
      System.out.println("| 0 - Sair        |");
      System.out.println("-------------------");

      opcao = in.nextInt();
      in.nextLine();

      int mdTag = -1;
      int mdLinha = -1;
      String entrada = "RESET";

      switch (opcao) {
        case 0:
          System.out.println("Saindo");
          break;
        case 1:
          // ! Mapeamento direto, com 9 bits para tag, 3 bits para linha
          mdTag = 9;
          mdLinha = 3;
          if (temVariosEnderecos) {
              for (String endereco : listaDeEnderecos) {
                direto(endereco, mdTag, mdLinha);
                System.out.println("----------------------------");
              }
            break;
          } else {
            System.out.println("digite endereço: ");
            entrada = in.nextLine();
            while (entrada.length() != 16) {
              System.out.println("Porfavor digite um endereco com 16 bits");
              entrada = in.nextLine();
            }
            direto(entrada, mdTag, mdLinha);
          }

          break;
        case 2:
          // ! Mapeamento direto, com 9 bits para tag, 4 bits para linha
          mdTag = 9;
          mdLinha = 4;
          if (temVariosEnderecos) {
              for (String endereco : listaDeEnderecos) {
                direto(endereco, mdTag, mdLinha);
                System.out.println("----------------------------");
              }            
            break;
          } else {
            System.out.println("digite endereço: ");
            entrada = in.nextLine();
            while (entrada.length() != 16) {
              System.out.println("Porfavor digite um endereco com 16 bits");
              entrada = in.nextLine();
            }
            direto(entrada, mdTag, mdLinha);
          }
          break;
        case 3:
          // ! Mapeamento associativo, com 12 bits para tag, 3 bits para palavra
          mdTag = 12;
          break;
        case 4:
          // ! Mapeamento associativo, com 13 bits para tag, 2 bits para palavra
          mdTag = 13;
          break;
        default:
          System.out.println("Opcao invalida");
          opcao = in.nextInt();
          break;
      }
    } while (opcao != 0);

    // * exemplo que da hit, String entrada = "0111111111111100";
    // * exemplo que da hit, String entrada = "0000000001010010";
  }

  public static void direto(String entrada, int mdTag, int mdLinha) {
    double hitCounter = 0;
    double missCounter = 0;
    int j = 0;

    for (String endereco : bin) {
      String result = mapeamentoDireto(endereco, entrada, mdTag, mdLinha);
      System.out.println("[" + j + "]-" + endereco + "-" + result);
      if (result.equalsIgnoreCase("HIT")) {
        hitCounter++;
      } else {
        missCounter++;
      }
      j++;
    }

    int numTotal = j;

    String porcentagem = "%";
    System.out.println("\nO endereco " + entrada + " tem " + hitCounter + " hits e " + missCounter + " misses.");
    System.out.printf("Porcentagem de hit:  %.2f", (hitCounter / numTotal) * 100);
    System.out.print(porcentagem + "\n");
    System.out.printf("Porcentagem de miss: %.2f", (missCounter / numTotal) * 100);
    System.out.print(porcentagem + "\n");
  }

  public static String mapeamentoDireto(String endereco, String entrada, int mdTag, int mdLinha) {
    String tagC = "";
    String lineC = "";
    String tag = "";
    String line = "";
    String result = "";
    int maxLinha = mdTag + mdLinha + 1;

    for (int i = 0; i < endereco.length(); i++) {
      if (i < mdTag) {
        tagC += endereco.charAt(i);
        tag += entrada.charAt(i);
      }
      if (i > mdTag && i < maxLinha) {
        lineC += endereco.charAt(i);
        line += entrada.charAt(i);
      }
    }

    if (tagC.equals(tag) && lineC.equals(line)) {
      result = "HIT";
    } else {
      result = "MISS";
    }

    return result;
  }

  // public static String mapeamentoAsociativo(String endereco, String entrada, int mdTag) {
  //   String tagC = "";
  //   String lineC = "";
  //   String tag = "";
  //   String line = "";
  //   String result = "";
  //   int maxLinha = mdTag + mdLinha + 1;

  //   for (int i = 0; i < endereco.length(); i++) {
  //     if (i < mdTag) {
  //       tagC += endereco.charAt(i);
  //       tag += entrada.charAt(i);
  //     }
  //     if (i > mdTag && i < maxLinha) {
  //       lineC += endereco.charAt(i);
  //       line += entrada.charAt(i);
  //     }
  //   }

  //   if (tagC.equals(tag) && lineC.equals(line)) {
  //     result = "HIT";
  //   } else {
  //     result = "MISS";
  //   }

  //   return result;
  // }

  public static void startup() throws IOException {
    String coisa = leitor(path);
    coisa = coisa.replaceAll(" ", "");
    vet = coisa.split(",");

    for (int i = 0; i < vet.length; i++) {
      bin.add(converte(vet[i]));
    }
  }

  public static void printarBonito(String[] vet) {
    for (int i = 0; i < vet.length; i++) {
      if (i % 8 == 0) {
        System.out.print("\n");
      }
      System.out.print("[ " + vet[i] + " ] ");
    }
  }

  public static void printarComInfo(String[] vet) {
    int i = 0;
    for (String string : bin) {
      System.out.println("[" + i + "]-" + string + "-" + vet[i]);
      i++;
    }
  }

  public static String leitor(String path) throws IOException {
    BufferedReader buffRead = new BufferedReader(new FileReader(path));
    String linha = "";
    String frase = "";
    while (true) {
      if (linha != null) {
        // System.out.println(linha);
        frase += linha;
      } else
        break;
      linha = buffRead.readLine();
    }
    buffRead.close();
    return frase;
  }

  public static String converte(String s) {
    String hexa = s;
    String binario = "";
    char bit;
    for (int i = 0; i < hexa.length(); i++) {
      bit = hexa.charAt(i);
      binario += letra(bit);
    }

    return binario;
  }

  public static String letra(char c) {
    String letra = "";
    if (c == '0') {
      return letra = "0000";
    }
    if (c == '1') {
      return letra = "0001";
    }
    if (c == '2') {
      return letra = "0010";
    }
    if (c == '3') {
      return letra = "0011";
    }
    if (c == '4') {
      return letra = "0100";
    }
    if (c == '5') {
      return letra = "0101";
    }
    if (c == '6') {
      return letra = "0110";
    }
    if (c == '7') {
      return letra = "0111";
    }
    if (c == '8') {
      return letra = "1000";
    }
    if (c == '9') {
      return letra = "1001";
    }
    if (c == 'a') {
      return letra = "1010";
    }
    if (c == 'b') {
      return letra = "1011";
    }
    if (c == 'c') {
      return letra = "1100";
    }
    if (c == 'd') {
      return letra = "1101";
    }
    if (c == 'e') {
      return letra = "1110";
    }
    if (c == 'f') {
      return letra = "1111";
    }
    return letra;
  }

}