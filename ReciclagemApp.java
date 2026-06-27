import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;

/**
 * ReciclagemApp — Interface gráfica principal da Calculadora PescaViva.
 *
 * <p>Aplicação desktop construída com Java Swing que oferece uma interface
 * moderna com tema escuro (Dark Mode) para cálculo de reciclagem de tampinhas PET.
 * Apresenta campos de entrada estilizados, validação visual de erros e
 * cards de resultado com destaque de alto contraste.</p>
 *
 * @author Eduardo Vinícius, Gabriel César, João Victor, Cauã Vital, Francisco Marques, Alex Ricardo
 * @version 2.0
 */
public class ReciclagemApp extends JFrame {

    // ========================== CAMPOS DA INTERFACE ==========================

    private JTextField campoGarrafas2L;
    private JTextField campoGarrafas1L;
    private JTextField campoGarrafasAgua;
    private JComboBox<CalculadoraReciclagem.PeriodoColeta> selectPeriodo;
    private JButton botaoCalcular;
    private JButton botaoLimpar;

    private JLabel labelPesoAno;
    private JLabel labelValorAno;

    // ========================== FORMATAÇÃO ==========================

    private static final DecimalFormat FORMATO_PESO  = new DecimalFormat("#,##0.000");
    private static final DecimalFormat FORMATO_VALOR = new DecimalFormat("R$ #,##0.00");

    // ========================== PALETA DE CORES (Dark Slate) ==========================

    private static final Color COLOR_BG             = new Color(15, 23, 42);      // Slate 900
    private static final Color COLOR_CARD           = new Color(30, 41, 59);      // Slate 800
    private static final Color COLOR_INPUT          = new Color(51, 65, 85);      // Slate 700
    private static final Color COLOR_BORDER         = new Color(71, 85, 105);     // Slate 600
    private static final Color COLOR_TEXT_PRIMARY    = new Color(241, 245, 249);   // Slate 100
    private static final Color COLOR_TEXT_MUTED      = new Color(148, 163, 184);   // Slate 400
    private static final Color COLOR_ACCENT          = new Color(16, 185, 129);    // Emerald 500
    private static final Color COLOR_ACCENT_HOVER    = new Color(5, 150, 105);     // Emerald 600
    private static final Color COLOR_ACCENT_LIGHT    = new Color(52, 211, 153);    // Emerald 400
    private static final Color COLOR_RESULT_VALUE    = new Color(34, 211, 238);    // Cyan 400
    private static final Color COLOR_FOCUS_RING      = new Color(99, 102, 241);    // Indigo 500
    private static final Color COLOR_DANGER          = new Color(239, 68, 68);     // Red 500
    private static final Color COLOR_CARD_GLOW_PESO  = new Color(16, 185, 129, 40);  // Emerald translúcido
    private static final Color COLOR_CARD_GLOW_VALOR = new Color(34, 211, 238, 40);  // Cyan translúcido

    // ========================== TIPOGRAFIA ==========================

    private static final Font FONT_TITLE         = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONT_SUBTITLE      = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_LABEL         = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_INPUT         = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BUTTON        = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_RESULT_LABEL  = new Font("Segoe UI", Font.BOLD, 11);
    private static final Font FONT_RESULT_VALUE  = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONT_FOOTER        = new Font("Segoe UI", Font.ITALIC, 11);

    // ========================== CONSTRUTOR ==========================

    public ReciclagemApp() {
        configurarRenderizacao();
        configurarJanela();
        iniciarComponentes();
        adicionarEventos();
        setVisible(true);
    }

    // ========================== CONFIGURAÇÃO ==========================

    /**
     * Habilita anti-aliasing global e renderização de qualidade para fontes.
     */
    private void configurarRenderizacao() {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {
            // Fallback silencioso para o look padrão
        }

        // Customizar cores dos tooltips e dialogs
        UIManager.put("ToolTip.background", COLOR_CARD);
        UIManager.put("ToolTip.foreground", COLOR_TEXT_PRIMARY);
        UIManager.put("ToolTip.border", new LineBorder(COLOR_BORDER, 1));
        UIManager.put("ToolTip.font", FONT_LABEL);
    }

    private void configurarJanela() {
        setTitle("\u267B PescaViva — Calculadora de Reciclagem de Tampinhas PET");
        setSize(580, 600);
        setMinimumSize(new Dimension(520, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
    }

    // ========================== MONTAGEM DA UI ==========================

    private void iniciarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(0, 12));
        painelPrincipal.setBackground(COLOR_BG);
        painelPrincipal.setBorder(new EmptyBorder(24, 28, 20, 28));

        painelPrincipal.add(criarPainelCabecalho(), BorderLayout.NORTH);
        painelPrincipal.add(criarPainelFormulario(), BorderLayout.CENTER);
        painelPrincipal.add(criarPainelInferior(), BorderLayout.SOUTH);

        this.add(painelPrincipal);
    }

    // ---------- Cabeçalho ----------

    private JPanel criarPainelCabecalho() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(COLOR_BG);
        painel.setBorder(new EmptyBorder(0, 0, 4, 0));

        JLabel icone = new JLabel("\u267B", JLabel.CENTER);
        icone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        icone.setForeground(COLOR_ACCENT_LIGHT);
        icone.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("PescaViva", JLabel.CENTER);
        titulo.setFont(FONT_TITLE);
        titulo.setForeground(COLOR_TEXT_PRIMARY);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Calculadora de Reciclagem de Tampinhas PET", JLabel.CENTER);
        subtitulo.setFont(FONT_SUBTITLE);
        subtitulo.setForeground(COLOR_TEXT_MUTED);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        painel.add(icone);
        painel.add(Box.createVerticalStrut(2));
        painel.add(titulo);
        painel.add(Box.createVerticalStrut(2));
        painel.add(subtitulo);

        return painel;
    }

    // ---------- Formulário ----------

    private JPanel criarPainelFormulario() {
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(COLOR_CARD);
        painelForm.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(COLOR_BORDER, 12),
                new EmptyBorder(18, 20, 18, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 6, 7, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Seção título do formulário
        JLabel secaoTitulo = new JLabel("\uD83D\uDCE6  Dados da Coleta");
        secaoTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        secaoTitulo.setForeground(COLOR_ACCENT_LIGHT);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 6, 12, 6);
        painelForm.add(secaoTitulo, gbc);
        gbc.insets = new Insets(7, 6, 7, 6);

        // Campos de entrada
        campoGarrafas2L = criarCampoTexto("Ex: 30");
        campoGarrafas2L.setToolTipText("Quantidade de tampinhas de garrafas PET de 2 litros");
        adicionarLinhaForm(painelForm, gbc, "Tampinhas de PET 2L:", campoGarrafas2L, 1);

        campoGarrafas1L = criarCampoTexto("Ex: 20");
        campoGarrafas1L.setToolTipText("Quantidade de tampinhas de garrafas PET de 1 litro");
        adicionarLinhaForm(painelForm, gbc, "Tampinhas de PET 1L:", campoGarrafas1L, 2);

        campoGarrafasAgua = criarCampoTexto("Ex: 50");
        campoGarrafasAgua.setToolTipText("Quantidade de tampinhas de garrafas de água mineral");
        adicionarLinhaForm(painelForm, gbc, "Tampinhas de Água:", campoGarrafasAgua, 3);

        // Seletor de período
        selectPeriodo = new JComboBox<>(CalculadoraReciclagem.PeriodoColeta.values());
        selectPeriodo.setFont(FONT_INPUT);
        selectPeriodo.setBackground(COLOR_INPUT);
        selectPeriodo.setForeground(COLOR_TEXT_PRIMARY);
        selectPeriodo.setBorder(new LineBorder(COLOR_BORDER, 1));
        selectPeriodo.setToolTipText("Frequência com que você descarta essas tampinhas");
        adicionarLinhaForm(painelForm, gbc, "Período de Coleta:", selectPeriodo, 4);

        // Botões de ação
        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 10, 0));
        painelBotoes.setBackground(COLOR_CARD);

        botaoCalcular = criarBotaoEstilizado("\u2728  Calcular Impacto", COLOR_ACCENT, COLOR_ACCENT_HOVER);
        botaoLimpar   = criarBotaoEstilizado("\u21BA  Limpar", COLOR_INPUT, COLOR_BORDER);

        painelBotoes.add(botaoCalcular);
        painelBotoes.add(botaoLimpar);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.insets = new Insets(14, 6, 4, 6);
        painelForm.add(painelBotoes, gbc);

        return painelForm;
    }

    // ---------- Painel Inferior (Resultados + Rodapé) ----------

    private JPanel criarPainelInferior() {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(COLOR_BG);

        // Cards de resultados
        JPanel painelResultados = new JPanel(new GridLayout(1, 2, 14, 0));
        painelResultados.setBackground(COLOR_BG);
        painelResultados.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        labelPesoAno  = new JLabel(FORMATO_PESO.format(0.0) + " kg", JLabel.CENTER);
        labelValorAno = new JLabel(FORMATO_VALOR.format(0.0), JLabel.CENTER);

        painelResultados.add(criarCardResultado("\u2696\uFE0F  Massa Anual", labelPesoAno, COLOR_ACCENT_LIGHT, COLOR_CARD_GLOW_PESO));
        painelResultados.add(criarCardResultado("\uD83D\uDCB0  Valor Anual", labelValorAno, COLOR_RESULT_VALUE, COLOR_CARD_GLOW_VALOR));

        // Rodapé
        JLabel rodape = new JLabel("PescaViva v1.0 — Projeto de Reciclagem de Tampinhas PET", JLabel.CENTER);
        rodape.setFont(FONT_FOOTER);
        rodape.setForeground(new Color(100, 116, 139)); // Slate 500
        rodape.setBorder(new EmptyBorder(10, 0, 0, 0));
        rodape.setAlignmentX(Component.CENTER_ALIGNMENT);

        wrapper.add(painelResultados);
        wrapper.add(rodape);

        return wrapper;
    }

    // ========================== FÁBRICAS DE COMPONENTES ==========================

    /**
     * Cria um campo de texto estilizado com placeholder (marca d'água).
     */
    private JTextField criarCampoTexto(String placeholder) {
        JTextField campo = new JTextField("", 12);
        campo.setFont(FONT_INPUT);
        campo.setBackground(COLOR_INPUT);
        campo.setForeground(COLOR_TEXT_MUTED);  // Inicia com cor de placeholder
        campo.setCaretColor(COLOR_TEXT_PRIMARY);
        campo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDER, 1),
                new EmptyBorder(7, 10, 7, 10)
        ));
        campo.setText(placeholder);

        // Comportamento de placeholder
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().startsWith("Ex:")) {
                    campo.setText("");
                    campo.setForeground(COLOR_TEXT_PRIMARY);
                }
                campo.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(COLOR_FOCUS_RING, 2),
                        new EmptyBorder(6, 9, 6, 9)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().trim().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(COLOR_TEXT_MUTED);
                }
                campo.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(COLOR_BORDER, 1),
                        new EmptyBorder(7, 10, 7, 10)
                ));
            }
        });

        return campo;
    }

    /**
     * Cria um botão estilizado com cores e efeito hover.
     */
    private JButton criarBotaoEstilizado(String texto, Color bgNormal, Color bgHover) {
        JButton botao = new JButton(texto);
        botao.setFont(FONT_BUTTON);
        botao.setBackground(bgNormal);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setOpaque(true);
        botao.setBorder(new EmptyBorder(10, 20, 10, 20));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                botao.setBackground(bgHover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                botao.setBackground(bgNormal);
            }
        });

        return botao;
    }

    /**
     * Cria um card de resultado com título, valor em destaque e glow sutil na borda.
     */
    private JPanel criarCardResultado(String titulo, JLabel valorLabel, Color corValor, Color corGlow) {
        JPanel card = new JPanel(new BorderLayout(4, 4)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fundo do card
                g2.setColor(COLOR_CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 14, 14));

                // Glow sutil na borda superior
                g2.setColor(corGlow);
                g2.fillRect(0, 0, getWidth(), 3);

                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(14, 14, 14, 14));

        JLabel labelTitulo = new JLabel(titulo, JLabel.CENTER);
        labelTitulo.setFont(FONT_RESULT_LABEL);
        labelTitulo.setForeground(COLOR_TEXT_MUTED);

        valorLabel.setFont(FONT_RESULT_VALUE);
        valorLabel.setForeground(corValor);

        card.add(labelTitulo, BorderLayout.NORTH);
        card.add(valorLabel, BorderLayout.CENTER);

        return card;
    }

    private void adicionarLinhaForm(JPanel painel, GridBagConstraints gbc, String textoLabel, JComponent comp, int linha) {
        gbc.gridwidth = 1;
        gbc.weightx = 0.4;
        gbc.gridx = 0;
        gbc.gridy = linha;

        JLabel label = new JLabel(textoLabel);
        label.setFont(FONT_LABEL);
        label.setForeground(COLOR_TEXT_PRIMARY);
        painel.add(label, gbc);

        gbc.weightx = 0.6;
        gbc.gridx = 1;
        painel.add(comp, gbc);
    }

    // ========================== EVENTOS ==========================

    private void adicionarEventos() {
        botaoCalcular.addActionListener(e -> executarCalculo());
        botaoLimpar.addActionListener(e -> limparFormulario());

        // Atalho: Enter para calcular
        getRootPane().setDefaultButton(botaoCalcular);
    }

    // ========================== LÓGICA ==========================

    private void executarCalculo() {
        try {
            int qtd2L   = parseEntrada(campoGarrafas2L);
            int qtd1L   = parseEntrada(campoGarrafas1L);
            int qtdAgua = parseEntrada(campoGarrafasAgua);

            CalculadoraReciclagem.PeriodoColeta periodo =
                    (CalculadoraReciclagem.PeriodoColeta) selectPeriodo.getSelectedItem();

            CalculadoraReciclagem calc = new CalculadoraReciclagem();
            CalculadoraReciclagem.Resultado resultado = calc.calcularAnual(qtd2L, qtd1L, qtdAgua, periodo);

            // Atualiza resultados com animação de cor
            labelPesoAno.setText(FORMATO_PESO.format(resultado.getPesoKgAnual()) + " kg");
            labelValorAno.setText(FORMATO_VALOR.format(resultado.getValorAnual()));

            // Flash visual para indicar sucesso
            flashLabel(labelPesoAno, Color.WHITE, COLOR_ACCENT_LIGHT);
            flashLabel(labelValorAno, Color.WHITE, COLOR_RESULT_VALUE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, digite apenas números inteiros válidos nos campos.",
                    "Entrada Inválida", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Valor Inválido", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro inesperado: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Extrai o valor numérico de um campo, ignorando texto de placeholder.
     */
    private int parseEntrada(JTextField campo) {
        String texto = campo.getText().trim();
        if (texto.isEmpty() || texto.startsWith("Ex:")) {
            return 0;
        }
        return Integer.parseInt(texto);
    }

    /**
     * Reseta todos os campos de entrada e resultados ao estado inicial.
     */
    private void limparFormulario() {
        resetarCampo(campoGarrafas2L, "Ex: 30");
        resetarCampo(campoGarrafas1L, "Ex: 20");
        resetarCampo(campoGarrafasAgua, "Ex: 50");
        selectPeriodo.setSelectedIndex(0);
        labelPesoAno.setText(FORMATO_PESO.format(0.0) + " kg");
        labelValorAno.setText(FORMATO_VALOR.format(0.0));
    }

    private void resetarCampo(JTextField campo, String placeholder) {
        campo.setText(placeholder);
        campo.setForeground(COLOR_TEXT_MUTED);
        campo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDER, 1),
                new EmptyBorder(7, 10, 7, 10)
        ));
    }

    /**
     * Efeito visual de "flash" temporário nos labels de resultado.
     * O label pisca em branco e retorna à cor original.
     */
    private void flashLabel(JLabel label, Color flashColor, Color originalColor) {
        label.setForeground(flashColor);
        Timer timer = new Timer(200, e -> label.setForeground(originalColor));
        timer.setRepeats(false);
        timer.start();
    }

    // ========================== BORDA ARREDONDADA CUSTOMIZADA ==========================

    /**
     * Implementação de borda arredondada com anti-aliasing para painéis.
     */
    private static class RoundedBorder extends AbstractBorder {
        private final Color color;
        private final int radius;

        RoundedBorder(Color color, int radius) {
            this.color = color;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(1, 1, 1, 1);
        }
    }

    // ========================== PONTO DE ENTRADA ==========================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReciclagemApp::new);
    }
}