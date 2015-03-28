package br.com.mentores;

public abstract class PessoaJuridica extends Pessoa
{
    private String CNPJ;
    private String nomeFantasia;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
    
    public PessoaJuridica() {
        this.CNPJ = "";
        this.nomeFantasia = "";
        this.inscricaoEstadual = "";
        this.inscricaoMunicipal = "";
    }
    
    public PessoaJuridica(final String nome, final String logradouro, final String numero, final String complemento, final String bairro, final String cidade, final Estado estado, final String CEP, final String telefone, final String email, final String CNPJ) throws Exception {
        super(nome, logradouro, numero, complemento, bairro, cidade, estado, CEP, telefone, email);
        this.setCNPJ(CNPJ);
    }
    
    public void setCNPJ(final String CNPJ) {
        if (CNPJ != null) {
            this.CNPJ = CNPJ.trim();
        }
    }
    
    public String getCNPJ() {
        return this.CNPJ;
    }
    
    public static String formatarCnpj(final String cnpj) {
        String cnpjFormatado = "";
        if (cnpj.length() == 14) {
            cnpjFormatado = String.valueOf(cnpjFormatado) + cnpj.substring(0, 2) + ".";
            cnpjFormatado = String.valueOf(cnpjFormatado) + cnpj.substring(2, 5) + ".";
            cnpjFormatado = String.valueOf(cnpjFormatado) + cnpj.substring(5, 8) + "/";
            cnpjFormatado = String.valueOf(cnpjFormatado) + cnpj.substring(8, 12) + "-";
            cnpjFormatado = String.valueOf(cnpjFormatado) + cnpj.substring(12);
        }
        return cnpjFormatado;
    }
    
    public static boolean validarCnpj(String cnpj) {
        if (cnpj == null) {
            return true;
        }
        if (cnpj.equals("")) {
            return true;
        }
        final char[] chrCnpj = cnpj.toCharArray();
        final int[] digitos = new int[15];
        int soma = 0;
        String numerosCnpj = "";
        for (int i = 0; i < chrCnpj.length; ++i) {
            if (Character.isDigit(chrCnpj[i])) {
                numerosCnpj = String.valueOf(numerosCnpj) + chrCnpj[i];
            }
        }
        cnpj = numerosCnpj;
        if (cnpj.length() != 14 && cnpj.length() != 15) {
            return false;
        }
        if (cnpj.equals("000000000000000") || cnpj.equals("00000000000000")) {
            return false;
        }
        if (cnpj.length() == 15) {
            cnpj = cnpj.substring(1);
        }
        digitos[0] = Integer.parseInt(cnpj.substring(0, 1));
        digitos[1] = Integer.parseInt(cnpj.substring(1, 2));
        digitos[2] = Integer.parseInt(cnpj.substring(2, 3));
        digitos[3] = Integer.parseInt(cnpj.substring(3, 4));
        digitos[4] = Integer.parseInt(cnpj.substring(4, 5));
        digitos[5] = Integer.parseInt(cnpj.substring(5, 6));
        digitos[6] = Integer.parseInt(cnpj.substring(6, 7));
        digitos[7] = Integer.parseInt(cnpj.substring(7, 8));
        digitos[8] = Integer.parseInt(cnpj.substring(8, 9));
        digitos[9] = Integer.parseInt(cnpj.substring(9, 10));
        digitos[10] = Integer.parseInt(cnpj.substring(10, 11));
        digitos[11] = Integer.parseInt(cnpj.substring(11, 12));
        digitos[12] = Integer.parseInt(cnpj.substring(12, 13));
        digitos[13] = Integer.parseInt(cnpj.substring(13));
        soma = 5 * digitos[0] + 4 * digitos[1] + 3 * digitos[2] + 2 * digitos[3] + 9 * digitos[4] + 8 * digitos[5] + 7 * digitos[6] + 6 * digitos[7] + 5 * digitos[8] + 4 * digitos[9] + 3 * digitos[10] + 2 * digitos[11];
        soma -= 11 * (soma / 11);
        int resultado;
        if (soma == 0 || soma == 1) {
            resultado = 0;
        }
        else {
            resultado = 11 - soma;
        }
        if (resultado == digitos[12]) {
            soma = digitos[0] * 6 + digitos[1] * 5 + digitos[2] * 4 + digitos[3] * 3 + digitos[4] * 2 + digitos[5] * 9 + digitos[6] * 8 + digitos[7] * 7 + digitos[8] * 6 + digitos[9] * 5 + digitos[10] * 4 + digitos[11] * 3 + digitos[12] * 2;
            soma -= 11 * (soma / 11);
            int resultadoDv;
            if (soma == 0 || soma == 1) {
                resultadoDv = 0;
            }
            else {
                resultadoDv = 11 - soma;
            }
            return resultadoDv == digitos[13];
        }
        return false;
    }
    
    public static String formatarCNPJ(final String cnpj) {
        String cnpjFormatado = "";
        if (!cnpj.equals("")) {
            cnpjFormatado = "-" + cnpj.substring(cnpj.length() - 2);
            cnpjFormatado = "/" + cnpj.substring(cnpj.length() - 6, cnpj.length() - 2) + cnpjFormatado;
            cnpjFormatado = "." + cnpj.substring(cnpj.length() - 9, cnpj.length() - 6) + cnpjFormatado;
            cnpjFormatado = "." + cnpj.substring(cnpj.length() - 12, cnpj.length() - 9) + cnpjFormatado;
            cnpjFormatado = String.valueOf(cnpj.substring(0, cnpj.length() - 12)) + cnpjFormatado;
        }
        return cnpjFormatado;
    }
    
    public String getNomeFantasia() {
        return this.nomeFantasia;
    }
    
    public void setNomeFantasia(final String nomeFantasia) {
        if (nomeFantasia != null) {
            this.nomeFantasia = nomeFantasia.trim();
        }
    }
    
    public String getInscricaoEstadual() {
        return this.inscricaoEstadual;
    }
    
    public String getInscricaoMunicipal() {
        return this.inscricaoMunicipal;
    }
    
    public static String formatarInscricaoMunicipal(final String inscricaoMunicipal) {
        String inscricaoFormatada = "";
        inscricaoFormatada = "-" + inscricaoMunicipal.substring(inscricaoMunicipal.length() - 1);
        inscricaoFormatada = "." + inscricaoMunicipal.substring(inscricaoMunicipal.length() - 4, inscricaoMunicipal.length() - 1) + inscricaoFormatada;
        inscricaoFormatada = String.valueOf(inscricaoMunicipal.substring(0, inscricaoMunicipal.length() - 4)) + inscricaoFormatada;
        return inscricaoFormatada;
    }
    
    public void setInscricaoEstadual(final String inscricaoEstadual) {
        if (inscricaoEstadual != null) {
            this.inscricaoEstadual = inscricaoEstadual.trim();
        }
    }
    
    public void setInscricaoMunicipal(final String inscricaoMunicipal) {
        if (inscricaoMunicipal != null) {
            this.inscricaoMunicipal = inscricaoMunicipal.trim();
        }
    }
}
