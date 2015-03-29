package org.esmerilprogramming.tecnolity.util

import java.util.*
import java.text.*
import java.util.regex.*

public class Calendario extends GregorianCalendar
{
  public static final String DOMINGO = "Dom"
  public static final String SEGUNDA = "Seg"
  public static final String TERCA = "Ter"
  public static final String QUARTA = "Qua"
  public static final String QUINTA = "Qui"
  public static final String SEXTA = "Sex"
  public static final String SABADO = "Sab"
  private String dataAtual
  private static int[] numeroDiasMes
  private static String[] nomeDiasSemana
  private static String[] nomeMeses

  static {
    Calendario.numeroDiasMes = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ]
    Calendario.nomeDiasSemana = [ "Domingo", "Segunda-feira", "Ter\u00e7a-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "S\u00e1bado" ]
    Calendario.nomeMeses = [ "Janeiro", "Fevereiro", "Mar\u00e7o", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" ]
  }

  public Calendario() {
  }

  public Calendario(final int ano, final int mes, final int dia) {
    super(ano, mes - 1, dia)
  }

  public Calendario(final int ano, final int mes, final int dia, final int hora, final int minuto) {
    super(ano, mes - 1, dia, hora, minuto)
  }

  public Calendario(final int ano, final int mes, final int dia, final int hora, final int minuto, final int segundo) {
    super(ano, mes - 1, dia, hora, minuto, segundo)
  }

  public Calendario(String data) {
    int hora = 0
    int minuto = 0
    int segundo = 0
    final int dia = Integer.parseInt(data.substring(0, data.indexOf("/")))
    data = data.substring(data.indexOf("/") + 1)
    final int mes = Integer.parseInt(data.substring(0, data.indexOf("/")))
    data = data.substring(data.indexOf("/") + 1)
    int ano
    if (data.indexOf(":") < 0 && data.indexOf(" ") < 0) {
      ano = Integer.parseInt(data)
    }
    else {
      ano = Integer.parseInt(data.substring(0, data.indexOf(" ")))
      data = data.substring(data.indexOf(" ") + 1)
      data = data.trim()
      if (data.indexOf(":") > 0) {
        hora = Integer.parseInt(data.substring(0, data.indexOf(":")))
        data = data.substring(data.indexOf(":") + 1)
        if (data.indexOf(":") > 0) {
          minuto = Integer.parseInt(data.substring(0, data.indexOf(":")))
          data = data.substring(data.indexOf(":") + 1)
          segundo = Integer.parseInt(data)
        }
        else if (!data.equals("")) {
          minuto = Integer.parseInt(data)
        }
      }
      else {
        hora = Integer.parseInt(data)
      }
    }
    super.set(1, ano)
    super.set(2, mes - 1)
    super.set(5, dia)
    super.set(11, hora)
    super.set(12, minuto)
    super.set(13, segundo)
    super.set(14, 0)
  }

  public Calendario(final Date data) {
    if (data != null) {
      this.setTime(data)
    }
  }

  public String get(final String formato) {
    return new SimpleDateFormat(formato).format(this.getTime())
  }

  public static Calendario getInstance(final String formato) {
    final Calendario calendario = new Calendario()
    if (formato.indexOf("y") < 0) {
      calendario.set(1, 0)
    }
    if (formato.indexOf("M") < 0) {
      calendario.set(2, 0)
    }
    if (formato.indexOf("d") < 0) {
      calendario.set(5, 0)
    }
    if (formato.indexOf("H") < 0 || formato.indexOf("h") < 0) {
      calendario.set(10, 0)
    }
    if (formato.indexOf("m") < 0) {
      calendario.set(12, 0)
    }
    if (formato.indexOf("s") < 0) {
      calendario.set(13, 0)
    }
    if (formato.indexOf("S") < 0) {
      calendario.set(14, 0)
    }
    return calendario
  }

  public Calendario redefinirElementos(final String formato) {
    if (formato.indexOf("y") < 0) {
      this.set(1, 0)
    }
    if (formato.indexOf("M") < 0) {
      this.set(2, 0)
    }
    if (formato.indexOf("d") < 0) {
      this.set(5, 0)
    }
    if (formato.indexOf("H") < 0 && formato.indexOf("h") < 0) {
      this.set(10, 0)
    }
    if (formato.indexOf("m") < 0) {
      this.set(12, 0)
    }
    if (formato.indexOf("s") < 0) {
      this.set(13, 0)
    }
    if (formato.indexOf("S") < 0) {
      this.set(14, 0)
    }
    return this
  }

  public Date getDate() {
    return this.getTime()
  }

  public String getDataAtualPorExtenso() {
    return "" + Calendario.nomeDiasSemana[this.get(7) - 1] + ", " + this.get(5) + " de " + Calendario.nomeMeses[this.get(2)] + " de " + this.get(1)
  }

  public static boolean compararDataMaiorIgual(final Calendario dataMaior, final Calendario dataMenor) {
    String dMaior = dataMaior.get("dd-MM-yyyy")
    String dMenor = dataMenor.get("dd-MM-yyyy")
    int ddMaior
    int mmMaior
    int aaaaMaior
    int ddMenor
    int mmMenor
    int aaaaMenor
    try {
      ddMaior = Integer.parseInt(dMaior.substring(0, dMaior.indexOf("-")))
      dMaior = dMaior.substring(dMaior.indexOf("-") + 1)
      mmMaior = Integer.parseInt(dMaior.substring(0, dMaior.indexOf("-")))
      dMaior = dMaior.substring(dMaior.indexOf("-") + 1)
      aaaaMaior = Integer.parseInt(dMaior)
      ddMenor = Integer.parseInt(dMenor.substring(0, dMenor.indexOf("-")))
      dMenor = dMenor.substring(dMenor.indexOf("-") + 1)
      mmMenor = Integer.parseInt(dMenor.substring(0, dMenor.indexOf("-")))
      dMenor = dMenor.substring(dMenor.indexOf("-") + 1)
      aaaaMenor = Integer.parseInt(dMenor)
    }
    catch (NumberFormatException e) {
      return false
    }
    if (aaaaMaior < aaaaMenor) {
      return false
    }
    if (aaaaMaior == aaaaMenor) {
      if (mmMaior < mmMenor) {
        return false
      }
      if (mmMaior == mmMenor) {
        if (ddMaior < ddMenor) {
          return false
        }
        if (ddMaior >= ddMenor) {
          return true
        }
      }
    }
    return true
  }

  public String getTratamento() {
    final int hora = this.get(11)
    if (hora >= 0 && hora < 12) {
      return "Bom dia"
    }
    if (hora >= 12 && hora < 18) {
      return "Boa tarde"
    }
    if (hora >= 18 && hora <= 23) {
      return "Boa noite"
    }
    return ""
  }

  public String getMesPorExtenso() {
    return Calendario.nomeMeses[this.get(2)]
  }

  public static int getNumeroDiaSemana(final String nomeDiaSemana) {
    for (int i = 0 i < Calendario.nomeDiasSemana.length ++i) {
      if (nomeDiaSemana.equals(Calendario.nomeDiasSemana[i])) {
        return i
      }
    }
    return -1
  }

  public int getNumeroDiasMes() {
    if (anoBisexto(this.get(1)) && this.get(2) == 1) {
      return 29
    }
    return Calendario.numeroDiasMes[this.get(2)]
  }

  public static int getNumeroDiasMes(final int mes, final int ano) {
    if (anoBisexto(ano) && mes == 1) {
      return 29
    }
    return Calendario.numeroDiasMes[mes]
  }

  public static int getNumeroSemanasAno() {
    final Calendario ano = new Calendario()
    ano.set(2, 11)
    ano.set(5, 25)
    final int numeroSemanasAno = ano.get(3)
    return numeroSemanasAno
  }

  public static int getBimestreMes(final int mes) {
    if (mes >= 12 || mes < 0) {
      return -1
    }
    if (mes == 0 || mes == 1) {
      return 1
    }
    if (mes == 2 || mes == 3) {
      return 2
    }
    if (mes == 4 || mes == 5) {
      return 3
    }
    if (mes == 6 || mes == 7) {
      return 4
    }
    if (mes == 8 || mes == 9) {
      return 5
    }
    return 6
  }

  public static int getTrimestreMes(final int mes) {
    if (mes >= 12 || mes < 0) {
      return -1
    }
    if (mes >= 0 && mes <= 2) {
      return 1
    }
    if (mes >= 3 && mes <= 5) {
      return 2
    }
    if (mes >= 6 && mes <= 8) {
      return 3
    }
    return 4
  }

  public static int getSemestreMes(final int mes) {
    if (mes >= 12 || mes < 0) {
      return -1
    }
    if (mes >= 0 && mes <= 5) {
      return 1
    }
    return 2
  }

  public static int getPrimeiroMesBimestre(final int mes) {
    if (mes >= 12 || mes < 0) {
      return -1
    }
    if (mes == 0 || mes == 1) {
      return 0
    }
    if (mes == 2 || mes == 3) {
      return 2
    }
    if (mes == 4 || mes == 5) {
      return 4
    }
    if (mes == 6 || mes == 7) {
      return 6
    }
    if (mes == 8 || mes == 9) {
      return 8
    }
    return 10
  }

  public static int getUltimoMesBimestre(final int mes) {
    if (mes >= 12 || mes < 0) {
      return -1
    }
    if (mes == 0 || mes == 1) {
      return 1
    }
    if (mes == 2 || mes == 3) {
      return 3
    }
    if (mes == 4 || mes == 5) {
      return 5
    }
    if (mes == 6 || mes == 7) {
      return 7
    }
    if (mes == 8 || mes == 9) {
      return 9
    }
    return 11
  }

  public static int getPrimeiroMesTrimestre(final int mes) {
    if (mes >= 12 || mes < 0) {
      return -1
    }
    if (mes >= 0 && mes <= 2) {
      return 0
    }
    if (mes >= 3 && mes <= 5) {
      return 3
    }
    if (mes >= 6 && mes <= 8) {
      return 6
    }
    return 9
  }

  public static int getUltimoMesTrimestre(final int mes) {
    if (mes >= 12 || mes < 0) {
      return -1
    }
    if (mes >= 0 && mes <= 2) {
      return 2
    }
    if (mes >= 3 && mes <= 5) {
      return 5
    }
    if (mes >= 6 && mes <= 8) {
      return 8
    }
    return 11
  }

  public static int getPrimeiroMesSemestre(final int mes) {
    if (mes >= 12 || mes < 0) {
      return -1
    }
    if (mes >= 0 && mes <= 5) {
      return 0
    }
    return 6
  }

  public static int getUltimoMesSemestre(final int mes) {
    if (mes >= 12 || mes < 0) {
      return -1
    }
    if (mes >= 0 && mes <= 5) {
      return 5
    }
    return 11
  }

  public String getDataAtual() {
    return this.dataAtual = new SimpleDateFormat("dd/MM/yyyy").format(this.getTime())
  }

  public static boolean validarData(String data, final String separador) {
    try {
      if (data.equals("")) {
        return true
      }
      int dd
      int mm
      int aaaa
      try {
        dd = Integer.parseInt(data.substring(0, data.indexOf(separador)))
        data = data.substring(data.indexOf(separador) + 1)
        mm = Integer.parseInt(data.substring(0, data.indexOf(separador)))
        data = data.substring(data.indexOf(separador) + 1)
        aaaa = Integer.parseInt(data)
      }
      catch (NumberFormatException e) {
        return false
      }
      catch (StringIndexOutOfBoundsException s) {
        return false
      }
      if (anoBisexto(aaaa)) {
        Calendario.numeroDiasMes[1] = 29
      }
      else {
        Calendario.numeroDiasMes[1] = 28
      }
      return aaaa >= 1900 && mm >= 1 && mm <= 12 && dd >= 1 && dd <= Calendario.numeroDiasMes[mm - 1]
    }
    catch (Exception e2) {
      return false
    }
  }

  public int compararCom(final Calendar data) {
    return this.getTime().compareTo(data.getTime())
  }

  public static boolean compararHora(final Calendario horaMaior, final Calendario horaMenor) {
    String hMaior = horaMaior.get("HH:mm")
    String hMenor = horaMenor.get("HH:mm")
    if (hMaior.equals("00:00")) {
      return true
    }
    int hhMaior
    int mmMaior
    int hhMenor
    int mmMenor
    try {
      hhMaior = Integer.parseInt(hMaior.substring(0, hMaior.indexOf(":")))
      hMaior = hMaior.substring(hMaior.indexOf(":") + 1)
      mmMaior = Integer.parseInt(hMaior)
      hhMenor = Integer.parseInt(hMenor.substring(0, hMenor.indexOf(":")))
      hMenor = hMenor.substring(hMenor.indexOf(":") + 1)
      mmMenor = Integer.parseInt(hMenor)
    }
    catch (NumberFormatException e) {
      return false
    }
    if (hhMaior < hhMenor) {
      return false
    }
    if (hhMaior == hhMenor) {
      if (mmMaior < mmMenor) {
        return false
      }
      if (mmMaior == mmMenor) {
        return false
      }
    }
    return true
  }

  public static boolean compararHoraMaiorIgual(final Calendario horaMaior, final Calendario horaMenor) {
    String hMaior = horaMaior.get("HH:mm")
    String hMenor = horaMenor.get("HH:mm")
    if (hMaior.equals("00:00")) {
      return true
    }
    int hhMaior
    int mmMaior
    int hhMenor
    int mmMenor
    try {
      hhMaior = Integer.parseInt(hMaior.substring(0, hMaior.indexOf(":")))
      hMaior = hMaior.substring(hMaior.indexOf(":") + 1)
      mmMaior = Integer.parseInt(hMaior)
      hhMenor = Integer.parseInt(hMenor.substring(0, hMenor.indexOf(":")))
      hMenor = hMenor.substring(hMenor.indexOf(":") + 1)
      mmMenor = Integer.parseInt(hMenor)
    }
    catch (NumberFormatException e) {
      return false
    }
    if (hhMaior < hhMenor) {
      return false
    }
    if (hhMaior == hhMenor) {
      if (mmMaior < mmMenor) {
        return false
      }
      if (mmMaior >= mmMenor) {
        return true
      }
    }
    return true
  }

  public static String numeroParaHora(final double numero) {
    final long hora = (long)numero
    String strHora = ""
    if (hora >= 0L) {
      final long minuto = Math.round((numero - hora) * 100.0) * 60L / 100L
      if (hora < 10L) {
        strHora = "0" + hora + ":"
      }
      else {
        strHora = String.valueOf(String.valueOf(hora)) + ":"
      }
      if (minuto < 10L) {
        strHora = String.valueOf(strHora) + "0" + minuto
      }
      else {
        strHora = String.valueOf(strHora) + minuto
      }
    }
    return strHora
  }

  public static long subtrairData(final Calendario primeiraData, final Calendario segundaData) {
    final Date dtMaior = primeiraData.getTime()
    final Date dtMenor = segundaData.getTime()
    final long diferenca = dtMaior.getTime() - dtMenor.getTime()
    return Math.abs(diferenca / 86400000L)
  }

  public static String inverterFormato(String data, final String separador) {
    if (!data.equals("") && !separador.equals("")) {
      final String dd = data.substring(0, data.indexOf(separador))
      data = data.substring(data.indexOf(separador) + 1)
      final String mm = data.substring(0, data.indexOf(separador))
      final String aaaa
      data = (aaaa = data.substring(data.indexOf(separador) + 1))
      return String.valueOf(mm) + separador + dd + separador + aaaa
    }
    return ""
  }

  public static String trocarSeparador(final String data, final char separadorAtual, final char separadorNovo) {
    String dataModificada = data
    if (!dataModificada.equals("")) {
      dataModificada = dataModificada.replace(separadorAtual, separadorNovo)
    }
    return dataModificada
  }

  public static boolean anoBisexto(final int ano) {
    return ano % 4 == 0
  }

  public static Calendario converterStringHora(String hora) {
    String hh = "0"
    String mm = "0"
    String ss = "0"
    hh = hora.substring(0, hora.indexOf(":"))
    hora = hora.substring(hora.indexOf(":") + 1)
    if (hora.indexOf(":") >= 0) {
      mm = hora.substring(0, hora.indexOf(":"))
      hora = (ss = hora.substring(hora.indexOf(":") + 1))
    }
    else {
      mm = hora
    }
    return new Calendario(0, 0, 0, Integer.parseInt(hh), Integer.parseInt(mm), Integer.parseInt(ss))
  }

  public static Calendario stringParaCalendario(final String data) {
    if (data == null) {
      return null
    }
    if (data.equals("")) {
      return new Calendario()
    }
    if (validarData(data, "/")) {
      final DateFormat dt = DateFormat.getInstance()
      try {
        return new Calendario(dt.parse(data))
      }
      catch (ParseException e) {
        return null
      }
    }
    return null
  }

  public static boolean validarHora(final String hora) {
    final Pattern padrao = Pattern.compile("(([0-1]{1}[0-9]{1})|(2[0-3]{1})):[0-5]{1}[0-9]{1}")
    final Matcher busca = padrao.matcher(hora)
    return busca.matches()
  }

  public static String getNomeMes(final int mes) {
    return Calendario.nomeMeses[mes - 1]
  }

  public String toString() {
    return this.get("dd/MM/yyyy")
  }
}
