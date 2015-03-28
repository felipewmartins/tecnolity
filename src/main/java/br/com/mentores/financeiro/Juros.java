package br.com.mentores.financeiro;

public abstract class Juros
{
    public static float calcularJurosCompostos(float capital, final int periodo, final float taxa) {
        float juros = 0.0f;
        for (int i = 0; i < periodo; ++i) {
            juros += capital * (taxa / 100.0f);
            capital += juros;
        }
        return juros;
    }
    
    public static float calcularJurosCompostos(float capital, final int periodo, final float[] taxas) {
        float juros = 0.0f;
        if (periodo == taxas.length) {
            for (int i = 0; i < periodo; ++i) {
                juros += capital * (taxas[i] / 100.0f);
                capital += juros;
            }
        }
        return juros;
    }
    
    public static float calcularJurosSimples(final float capital, final int periodo, final float taxa) {
        final float juros = capital * periodo * (taxa / 100.0f);
        return juros;
    }
}
