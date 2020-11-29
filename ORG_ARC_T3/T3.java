
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class T3 {

	public static String leitor(String path) throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
    String linha = "";
    String frase="";
		while (true) {
			if (linha != null) {
				//System.out.println(linha);
        frase+=linha;
			} else
				break;
			linha = buffRead.readLine();
		}
    buffRead.close();
    return frase;
	}

  public static void main(String[] args) throws IOException {
    Scanner in = new Scanner(System.in);
    String path="Arquivo.txt";
  
    System.out.println("digite endereço: ");

    String endereço="0001100101010010";


    String coisa = leitor(path);
    coisa= coisa.replaceAll(" ", "");
    String vet[]=coisa.split(",");
    //  for (int i = 0; i < vet.length; i++) {
    //    if(i%8 ==0 ){
    //      System.out.print("\n");
    //    }
    //      System.out.print("[ "+vet[i]+" ] ");
    //  }

    ArrayList<String> bin = new ArrayList<String>();
    for (int i = 0; i < vet.length; i++) {
        bin.add(converte(vet[i]));
    }
    int i=0;
    for (String string : bin) {
      
      System.out.println("["+i+"]-"+string+"-"+ vet[i]);
      i++;
    }

  }

  public static String letra(char c){
    String letra = "";
    if(c=='0'){
      return letra= "0000";
    }if(c=='1'){
      return letra= "0001";
    }if(c=='2'){
      return letra= "0010";
    }if(c=='3'){
      return letra= "0011";
    }if(c=='4'){
      return letra= "0100";
    }if(c=='5'){
      return letra= "0101";
    }if(c=='6'){
      return letra= "0110";
    }if(c=='7'){
      return letra= "0111";
    }if(c=='8'){
      return letra= "1000";
    }if(c=='9'){
      return letra= "1001";
    }if(c=='a'){
      return letra= "1010";
    }if(c=='b'){
      return letra= "1011";
    }if(c=='c'){
      return letra= "1100";
    }if(c=='d'){
      return letra= "1101";
    }if(c=='e'){
      return letra= "1110";
    }if(c=='f'){
      return letra= "1111";
    }
    return letra;
  }


  public static String converte(String s){
    String hexa= s;
    String binario="";
    char bit;
    for (int i = 0; i < hexa.length(); i++) {
      bit= hexa.charAt(i);
      binario+=letra(bit) ;
    }

    return binario;
  }

  public static String mapeamentoDireto( String endereco, String entrada){
    String tag = "";
    String line = "";
      for (int i = 0; i < endereco.length(); i++) {

            if(i<9){
              tag+= endereco.charAt(i);
            }
            if(i>9 && i<13){
              line+= endereco.charAt(i);
            }
      }
      return "s";
  }

}

  
