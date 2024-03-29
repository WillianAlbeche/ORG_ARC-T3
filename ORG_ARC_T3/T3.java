/* 
*==========================================================
* Codigo by Willian Magnum Albeche, Arthur Schossler Dutra
*==========================================================
*/


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class T3 {

  private static String path = "ORG_ARC_T3/Arquivo.txt";
  private static Scanner in = new Scanner(System.in);
  private static ArrayList<String> bin = new ArrayList<String>();
  private static ArrayList<String> listaDeEnderecos = new ArrayList<String>();
  private static String[] vet;
  private static ArrayList<cacheLine> cache = new ArrayList<cacheLine>();

  public static void main(String[] args) throws IOException {
    startup();
    int opcao;

    // printarComInfo(bin);
    // System.out.println("\n\n\n\n");

    int mdTag = -1;
    int mdLinha = -1;
    do {

      System.out.println("\n-------------------------------------------------------");
      System.out.println("| 1 - Operacao 1- Mapeamento direto linha = 3 bits    |");
      System.out.println("| 2 - Operacao 2- Mapeamento direto linha = 4 bits    |");
      System.out.println("| 3 - Operacao 3- Mapemeamento associativo tag = 12   |");
      System.out.println("| 4 - Operacao 4- Mapemeamento associativo tag = 13   |");
      System.out.println("| 0 - Sair                                            |");
      System.out.println("-------------------------------------------------------\n");

      opcao = in.nextInt();
      in.nextLine();

      switch (opcao) {
        case 0:
          System.out.println("Saindo");
          break;
        case 1:
          // ! Mapeamento direto, com 9 bits para tag, 3 bits para linha
          mdTag = 9;
          mdLinha = 3;
          direto(mdTag, mdLinha);
          break;
        case 2:
          // ! Mapeamento direto, com 9 bits para tag, 4 bits para linha
          mdTag = 9;
          mdLinha = 4;
          direto(mdTag, mdLinha);
          break;
        case 3:
          // ! Mapeamento associativo, com 12 bits para tag
          mdTag = 12;
          asociativo(mdTag);
          break;
        case 4:
          // ! Mapeamento associativo, com 13 bits para tag
          mdTag = 13;
          asociativo(mdTag);
          break;
        default:
          System.out.println("\nOpcao invalida");
          break;
      }

    } while (opcao != 0);

  }

  public static String addicionarAoCache(String data, String tag, String line) {
    String resultado = "Erro";

    for (cacheLine cacheD : cache) {
      if (cacheD.getTag().equals(tag) && cacheD.getLine().equals(line)) {
        resultado = "HIT";
      }
    }
    if (cache.size() == 0) {
      cacheLine cacheAdd = new cacheLine(tag, data, line);
      cache.add(cacheAdd);
      resultado = "MISS";
    } else if (!resultado.equals("HIT")) {
      cacheLine cacheAdd = new cacheLine(tag, data, line);
      cache.add(cacheAdd);
      return "MISS";
    }

    return resultado;
  }

  public static String addicionarAoCacheAssociativo(String data, String tag) {
    String resultado = "Erro";

    for (cacheLine cacheD : cache) {

      if (cacheD.getTag().equals(tag)) {
        resultado = "HIT";
      }
    }
    if (cache.size() == 0) {
      cacheLine cacheAdd = new cacheLine(tag, data);
      cache.add(cacheAdd);
      resultado = "MISS";
    } else if (!resultado.equals("HIT")) {
      cacheLine cacheAdd = new cacheLine(tag, data);
      cache.add(cacheAdd);
      return "MISS";
    }

    return resultado;
  }

  // * exemplo que da hit, String entrada = "011111111 1111100";
  // * exemplo que da hit, String entrada = "0000000001010010";

  public static void direto(int mdTag, int mdLinha) {
    DecimalFormat hitFormat = new DecimalFormat("#.##%");
    DecimalFormat missFormat = new DecimalFormat("#.##%");

    double hitCounter = 0;
    double missCounter = 0;
    int j = 0;

    System.out.println("\n________________________________________________");
    System.out.println("Linha  | Tag       | Palavras        | Resultado ");
    // System.out.println("000 | 123456789 | 1234567890123456 | 0 ");
    for (String endereco : bin) {
      String tag = endereco.substring(0, 9);
      String palavras = "temp";
      String linha = "temp";
      if (mdLinha == 3) { // * DIRETO 1
        palavras = endereco.substring(0, 12);
        linha = endereco.substring(9, 12);
        palavras += "XXX";
      } else if (mdLinha == 4) { // * DIRETO 2
        palavras = endereco.substring(0, 13);
        linha = endereco.substring(9, 13);
        palavras += "XX";
      }

      String result = addicionarAoCache(palavras, tag, linha);

      System.out.println(linha + "   | " + tag + " | " + palavras + " | " + result);

      if (result.equalsIgnoreCase("HIT")) {
        hitCounter++;

      } else if (result.equalsIgnoreCase("MISS")) {
        missCounter++;
      } else
        System.out.println("Deu ruim :(");
      j++;
    }

    int numTotal = j;
    System.out.println("\n=============================");
    System.out.println("counter hit: " + hitCounter);
    System.out.println("counter miss: " + missCounter);
    System.out.println("Porcentagem de hit:  " + hitFormat.format((hitCounter / numTotal)));
    System.out.println("Porcentagem de miss:  " + missFormat.format((missCounter / numTotal)));
    System.out.println("=============================\n");
    
  }

  public static void asociativo(int mdTag) {
    DecimalFormat hitFormat = new DecimalFormat("#.##%");
    DecimalFormat missFormat = new DecimalFormat("#.##%");

    double hitCounter = 0;
    double missCounter = 0;
    int j = 0;

    System.out.println("\n___________________________________________");
    System.out.println("\n Tag          | Palavras         | Resultado ");
    for (String endereco : bin) {
      String tag = "temp";
      String palavras = "temp";
      if (mdTag == 12) {
        tag = endereco.substring(0, 12);
        palavras = endereco.substring(0, 12);
        palavras += "XXX";
      } else if (mdTag == 13) {
        tag = endereco.substring(0, 13);
        palavras = endereco.substring(0, 13);
        palavras += "XX";
      }
      String result = addicionarAoCacheAssociativo(palavras, tag);
      // ! VER ISSO

      System.out.println(tag + " | " + palavras + " | " + result);

      if (result.equalsIgnoreCase("HIT")) {
        hitCounter++;
      } else {
        missCounter++;
      }
      if (result.equalsIgnoreCase("ERRO")) {
        System.out.println("ERROOO");
      }
      j++;
    }

    int numTotal = j;

    // System.out.println("\nO endereco " + entrada + " tem " + hitCounter + " hits
    // e " + missCounter + " misses.");
    System.out.println("\n==================================");
    System.out.println("counter hit: " + hitCounter);
    System.out.println("counter miss: " + missCounter);
    System.out.println("Porcentagem de hit:  " + hitFormat.format((hitCounter / numTotal)));
    System.out.println("Porcentagem de miss:  " + missFormat.format((missCounter / numTotal)));
    System.out.println("==================================\n");
  }

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

  public static void printarComInfo(ArrayList<String> vet) {
    int i = 0;
    for (String string : bin) {
      System.out.println("[" + i + "]-" + string + "\n");
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