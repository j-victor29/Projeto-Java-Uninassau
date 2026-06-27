/**
 * CalculadoraReciclagem — Motor de cálculo para reciclagem de tampinhas PET.
 *
 * <p>Responsável por converter quantidades de tampinhas plásticas em projeções
 * anuais de massa (kg) e retorno financeiro (R$), com base em fatores de
 * conversão padronizados do mercado de reciclagem.</p>
 *
 * <h3>Fatores de Conversão:</h3>
 * <ul>
 *   <li>Tampinhas de garrafas 1L e 2L: 500 unidades = 1 kg</li>
 *   <li>Tampinhas de garrafas de água mineral: 1.000 unidades = 1 kg</li>
 *   <li>Preço de mercado: R$ 0,98 por kg reciclado</li>
 * </ul>
 *
 * @author Eduardo Vinícius, Gabriel César, João Victor, Cauã Vital, Francisco Marques, Alex Ricardo
 * @version 1.0
 */
public class CalculadoraReciclagem {

    // ========================== CONSTANTES DE CONVERSÃO ==========================

    /** Quantidade de tampinhas de garrafas grandes (1L/2L) necessárias para compor 1 kg. */
    private static final double TAMPINHAS_GRANDES_POR_KG = 500.0;

    /** Quantidade de tampinhas de garrafas de água mineral necessárias para compor 1 kg. */
    private static final double TAMPINHAS_AGUA_POR_KG = 1000.0;

    /** Valor de mercado por quilograma de tampinhas PET recicladas (em R$). */
    private static final double PRECO_KG = 0.98;

    // ========================== ENUM DE PERÍODO ==========================

    /**
     * Representa os períodos de coleta suportados pelo sistema.
     * Cada período possui um multiplicador que converte a quantidade informada
     * em uma projeção anual equivalente.
     */
    public enum PeriodoColeta {
        /** Coleta diária — multiplicador de 365 (dias/ano). */
        DIARIO(365.0, "Diário"),

        /** Coleta semanal — multiplicador de 52 (semanas/ano). */
        SEMANAL(52.0, "Semanal"),

        /** Coleta mensal — multiplicador de 12 (meses/ano). */
        MENSAL(12.0, "Mensal"),

        /** Coleta anual — multiplicador de 1 (já representa o ano). */
        ANUAL(1.0, "Anual");

        private final double multiplicadorAnual;
        private final String descricao;

        PeriodoColeta(double multiplicadorAnual, String descricao) {
            this.multiplicadorAnual = multiplicadorAnual;
            this.descricao = descricao;
        }

        /**
         * Retorna o fator de multiplicação para projeção anual.
         * @return multiplicador anual correspondente ao período
         */
        public double getMultiplicador() {
            return multiplicadorAnual;
        }

        /**
         * Exibe o nome amigável do período no seletor da interface.
         * @return descrição legível do período (ex: "Diário", "Semanal")
         */
        @Override
        public String toString() {
            return descricao;
        }
    }

    // ========================== CÁLCULO PRINCIPAL ==========================

    /**
     * Calcula a projeção anual de massa reciclável e valor financeiro estimado.
     *
     * @param tampinhas2L   quantidade de tampinhas de garrafas PET de 2L
     * @param tampinhas1L   quantidade de tampinhas de garrafas PET de 1L
     * @param tampinhasAgua quantidade de tampinhas de garrafas de água mineral
     * @param periodo       período de coleta selecionado pelo usuário
     * @return objeto {@link Resultado} com peso anual (kg) e valor anual (R$)
     * @throws IllegalArgumentException se qualquer quantidade for negativa
     */
    public Resultado calcularAnual(
            int tampinhas2L,
            int tampinhas1L,
            int tampinhasAgua,
            PeriodoColeta periodo
    ) {
        if (tampinhas2L < 0 || tampinhas1L < 0 || tampinhasAgua < 0) {
            throw new IllegalArgumentException("As quantidades não podem ser negativas.");
        }

        double pesoGrandes = (tampinhas2L + tampinhas1L) / TAMPINHAS_GRANDES_POR_KG;
        double pesoAgua    = tampinhasAgua / TAMPINHAS_AGUA_POR_KG;
        double pesoTotal   = pesoGrandes + pesoAgua;
        double pesoAnual   = pesoTotal * periodo.getMultiplicador();
        double valorAnual  = pesoAnual * PRECO_KG;

        return new Resultado(pesoAnual, valorAnual);
    }

    // ========================== CLASSE DE RESULTADO ==========================

    /**
     * Encapsula o resultado de uma projeção anual de reciclagem.
     * Imutável por design — valores definidos apenas no construtor.
     */
    public static class Resultado {

        private final double pesoEmKgAno;
        private final double valorEmReaisAno;

        /**
         * Cria um novo resultado de projeção anual.
         * @param pesoEmKgAno    massa total em quilogramas projetada para o ano
         * @param valorEmReaisAno valor financeiro em reais projetado para o ano
         */
        public Resultado(double pesoEmKgAno, double valorEmReaisAno) {
            this.pesoEmKgAno = pesoEmKgAno;
            this.valorEmReaisAno = valorEmReaisAno;
        }

        /** @return massa anual projetada em quilogramas */
        public double getPesoKgAnual() {
            return pesoEmKgAno;
        }

        /** @return valor financeiro anual projetado em reais */
        public double getValorAnual() {
            return valorEmReaisAno;
        }

        /**
         * Representação textual do resultado para debug e logging.
         * @return string formatada com peso e valor anuais
         */
        @Override
        public String toString() {
            return String.format("Resultado { peso=%.3f kg/ano, valor=R$ %.2f/ano }", pesoEmKgAno, valorEmReaisAno);
        }
    }
}