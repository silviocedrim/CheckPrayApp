package br.org.maanaim.checkpray.util;

/**
 * Created by Silvinho Cedrim on 04/07/2017.
 */

public class DataUtil {

    public static String getSiglaMes(int mes){
        String[] meses = {"JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL",
                "AGO", "SET", "OUT", "NOV", "DEZ"};
        return meses[mes];
    }

    public static String getNomeMes(int mes){
        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho",
                "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        return meses[mes];
    }
}
