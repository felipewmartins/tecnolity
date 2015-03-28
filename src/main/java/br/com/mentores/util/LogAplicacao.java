package br.com.mentores.util;

import java.io.*;

public class LogAplicacao extends PrintStream
{
    static OutputStream arquivoLog;
    static PrintStream saida;
    static PrintStream erro;
    
    LogAplicacao(final PrintStream ps) {
        super(ps);
    }
    
    public static void start(final String arquivo) throws IOException {
        LogAplicacao.saida = System.out;
        LogAplicacao.erro = System.err;
        final File logFolder = new File("log");
        if (!logFolder.isDirectory()) {
            logFolder.mkdir();
        }
        LogAplicacao.arquivoLog = new PrintStream(new BufferedOutputStream(new FileOutputStream(arquivo)));
        System.setOut(new LogAplicacao(System.out));
        System.setErr(new LogAplicacao(System.err));
    }
    
    public static void stop() {
        if (LogAplicacao.saida != null || LogAplicacao.erro != null) {
            System.setOut(LogAplicacao.saida);
            System.setErr(LogAplicacao.erro);
            try {
                LogAplicacao.arquivoLog.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void write(final int b) {
        try {
            LogAplicacao.arquivoLog.write(b);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.setError();
        }
        super.write(b);
    }
    
    public void write(final byte[] buf, final int off, final int len) {
        try {
            LogAplicacao.arquivoLog.write(buf, off, len);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.setError();
        }
        super.write(buf, off, len);
    }
}
