package br.org.maanaim.checkpray.util;

import java.io.Serializable;

/**
 * Created by Silvinho Cedrim on 10/07/2017.
 */

public enum GrauPertenca implements Serializable{

    IRMAO("Irmão"),
    VOCACIONADO("Vocacionado"),
    MISSIONARIO("Missionário"),
    CONSAGRADO("Consagrado"),
    OUTROS("Outros");

    private String label;

    GrauPertenca(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }

    public static GrauPertenca getValue(int position) {

        GrauPertenca grauPertenca = null;
        switch (position) {
            case 1:
                grauPertenca = GrauPertenca.IRMAO;
                break;
            case 2:
                grauPertenca = GrauPertenca.VOCACIONADO;
                break;
            case 3:
                grauPertenca = GrauPertenca.MISSIONARIO;
                break;
            case 4:
                grauPertenca = GrauPertenca.CONSAGRADO;
                break;

        }

        return grauPertenca;
    }


}
