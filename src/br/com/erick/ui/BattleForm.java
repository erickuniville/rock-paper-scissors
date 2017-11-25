package br.com.erick.ui;

import br.com.erick.core.Battle;
import br.com.erick.core.EnemyIntelligence;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static br.com.erick.constants.Constants.PAPER;
import static br.com.erick.constants.Constants.ROCK;
import static br.com.erick.constants.Constants.SCISSORS;

/**
 * Created by erick.budal on 31/10/2017.
 */
public class BattleForm {
    private JPanel panelMain;
    private JButton buttonRock;
    private JButton buttonPaper;
    private JButton buttonScissor;
    private JCheckBox checkBoxReverse;
    private JButton buttonFight;
    private JTable tableStatsPlayer;
    private JTable tableStatsEnemy;
    private JTextArea textAreaOutput;
    private JLabel labelStatsEnemy;
    private JLabel labelStatsMonster;
    private JProgressBar progressBarPlayerLP;
    private JProgressBar progressBarEnemyLP;
    private Battle battle;
    private JFrame frame;
    private ChooseTeamForm chooseTeamForm;

    private String chosenButtonColor = "0x9bf0ff";
    private int chosenMonsterType = -1;
    private EnemyIntelligence enemyIntelligence;

    public BattleForm(Battle battle, ChooseTeamForm chooseTeamForm) {
        this.battle = battle;
        this.enemyIntelligence = new EnemyIntelligence();

        $$$setupUI$$$();
        Border outsideBorder = BorderFactory.createLineBorder(Color.BLACK);
        Border insideBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        textAreaOutput.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));

        progressBarPlayerLP.setValue(battle.getPlayerA().getLifePoints());
        progressBarEnemyLP.setValue(battle.getPlayerB().getLifePoints());

        frame = new JFrame("BattleForm");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

        buttonRock.addActionListener(e -> monsterTypeButtonClicked(buttonRock));
        buttonPaper.addActionListener(e -> monsterTypeButtonClicked(buttonPaper));
        buttonScissor.addActionListener(e -> monsterTypeButtonClicked(buttonScissor));
        buttonFight.addActionListener(e -> fightButtonClicked());

        this.chooseTeamForm = chooseTeamForm;
    }

    private void createUIComponents() {
        String[] columnsPlayer = {"TYPE", "ATK", "DEF", "USE"};
        Object[][] dataPlayer = {
                {"TYPE", "ATK", "DEF", "USE"},
                {"Rock", battle.getPlayerA().getRockMonster().getStrength(), battle.getPlayerA().getRockMonster().getDefense(), true},
                {"Paper", battle.getPlayerA().getPaperMonster().getStrength(), battle.getPlayerA().getPaperMonster().getDefense(), true},
                {"Scissor", battle.getPlayerA().getScissorMonster().getStrength(), battle.getPlayerA().getScissorMonster().getDefense(), true}
        };
        DefaultTableModel modelPlayer = new DefaultTableModel(dataPlayer, columnsPlayer);
        tableStatsPlayer = new JTable(modelPlayer);

        String[] columnsEnemy = {"TYPE", "ATK", "DEF", "USE"};
        Object[][] dataEnemy = {
                {"TYPE", "ATK", "DEF", "USE"},
                {"Rock", battle.getPlayerB().getRockMonster().getStrength(), battle.getPlayerB().getRockMonster().getDefense(), true},
                {"Paper", battle.getPlayerB().getPaperMonster().getStrength(), battle.getPlayerB().getPaperMonster().getDefense(), true},
                {"Scissor", battle.getPlayerB().getScissorMonster().getStrength(), battle.getPlayerB().getScissorMonster().getDefense(), true}
        };
        DefaultTableModel modelEnemy = new DefaultTableModel(dataEnemy, columnsEnemy);
        tableStatsEnemy = new JTable(modelEnemy);
    }

    private void monsterTypeButtonClicked(JButton monsterTypeButton) {
        monsterTypeButton.setBackground(Color.decode(chosenButtonColor));

        ColorUIResource defaultColor = new ColorUIResource(238, 238, 238);

        if (monsterTypeButton.getName().toLowerCase().equals("rock")) {
            if (chosenMonsterType == PAPER) {
                buttonPaper.setBackground(defaultColor);
            } else if (chosenMonsterType == SCISSORS) {
                buttonScissor.setBackground(defaultColor);
            }
            chosenMonsterType = ROCK;
        } else if (monsterTypeButton.getName().toLowerCase().equals("paper")) {
            if (chosenMonsterType == ROCK) {
                buttonRock.setBackground(defaultColor);
            } else if (chosenMonsterType == SCISSORS) {
                buttonScissor.setBackground(defaultColor);
            }
            chosenMonsterType = PAPER;
        } else {
            if (chosenMonsterType == ROCK) {
                buttonRock.setBackground(defaultColor);
            } else if (chosenMonsterType == PAPER) {
                buttonPaper.setBackground(defaultColor);
            }
            chosenMonsterType = SCISSORS;
        }
    }

    private void fightButtonClicked() {

        boolean battleEnded = false;

        if (chosenMonsterType == -1) {
            JOptionPane.showMessageDialog(frame, "Please, select your monster!", "How do you dare?", JOptionPane.ERROR_MESSAGE);
            return;
        }

        battle.fight(chosenMonsterType, enemyIntelligence.chooseMonster(), checkBoxReverse.isSelected(), enemyIntelligence.chooseIfUseReverse());

        textAreaOutput.setText(battle.getLogString());

        //DRAW
        if (battle.getPlayerA().getLifePoints() <= 0 && battle.getPlayerB().getLifePoints() <= 0) {
            progressBarPlayerLP.setString("0");
            progressBarPlayerLP.setValue(0);

            progressBarEnemyLP.setString("0");
            progressBarEnemyLP.setValue(0);

            textAreaOutput.append("\n\nD R A W");

            Border outsideBorder = BorderFactory.createLineBorder(Color.BLUE);
            Border insideBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
            textAreaOutput.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));

            battleEnded = true;
        }

        if (battle.getPlayerA().getLifePoints() >= 0) {
            progressBarPlayerLP.setString(Integer.toString(battle.getPlayerA().getLifePoints()));
            progressBarPlayerLP.setValue(battle.getPlayerA().getLifePoints());
        } else { //LOSE
            progressBarPlayerLP.setString("0");
            progressBarPlayerLP.setValue(0);

            textAreaOutput.append("\n\nY O U   L O S E !");

            Border outsideBorder = BorderFactory.createLineBorder(Color.RED);
            Border insideBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
            textAreaOutput.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));

            battleEnded = true;
        }

        if (battle.getPlayerB().getLifePoints() >= 0) {
            progressBarEnemyLP.setString(Integer.toString(battle.getPlayerB().getLifePoints()));
            progressBarEnemyLP.setValue(battle.getPlayerB().getLifePoints());
        } else { //WIN
            progressBarEnemyLP.setString("0");
            progressBarEnemyLP.setValue(0);

            textAreaOutput.append("\n\nC O N G R A T U L A T I O N S !   Y O U   W I N !");

            Border outsideBorder = BorderFactory.createLineBorder(Color.GREEN);
            Border insideBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
            textAreaOutput.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));

            battleEnded = true;
        }

        buttonRock.setEnabled(battle.getPlayerA().getRockMonster().isCanUse());
        buttonPaper.setEnabled(battle.getPlayerA().getPaperMonster().isCanUse());
        buttonScissor.setEnabled(battle.getPlayerA().getScissorMonster().isCanUse());

        boolean thisRoundEnded = !battle.getPlayerA().getRockMonster().isCanUse() &&
                !battle.getPlayerA().getPaperMonster().isCanUse() &&
                !battle.getPlayerA().getScissorMonster().isCanUse() &&
                battle.getActualTurn() == 3;
        
        System.out.println("\n\nthisRoundEnded: " + thisRoundEnded);
        System.out.println("actualTurn: " + battle.getActualTurn() + "\n\n");

        if (thisRoundEnded) {
            buttonRock.setEnabled(true);
            buttonPaper.setEnabled(true);
            buttonScissor.setEnabled(true);

            ColorUIResource defaultColor = new ColorUIResource(238, 238, 238);
            buttonRock.setBackground(defaultColor);
            buttonPaper.setBackground(defaultColor);
            buttonScissor.setBackground(defaultColor);

            checkBoxReverse.setSelected(false);
            checkBoxReverse.setEnabled(true);

            enemyIntelligence = new EnemyIntelligence();

            battle.resetMonstersAvailability();
        }

        if (checkBoxReverse.isSelected()) {
            checkBoxReverse.setSelected(false);
            checkBoxReverse.setEnabled(false);
        }

        DefaultTableModel playerModel = (DefaultTableModel) tableStatsPlayer.getModel();
        playerModel.setValueAt(battle.getPlayerA().getRockMonster().isCanUse(), 1, 3);
        playerModel.setValueAt(battle.getPlayerA().getPaperMonster().isCanUse(), 2, 3);
        playerModel.setValueAt(battle.getPlayerA().getScissorMonster().isCanUse(), 3, 3);

        DefaultTableModel enemyModel = (DefaultTableModel) tableStatsEnemy.getModel();
        enemyModel.setValueAt(battle.getPlayerB().getRockMonster().isCanUse(), 1, 3);
        enemyModel.setValueAt(battle.getPlayerB().getPaperMonster().isCanUse(), 2, 3);
        enemyModel.setValueAt(battle.getPlayerB().getScissorMonster().isCanUse(), 3, 3);

        chosenMonsterType = -1;

        if (battleEnded) {
        	battle.getPlayerA().getRockMonster().setCanUse(true);
        	battle.getPlayerA().getPaperMonster().setCanUse(true);
        	battle.getPlayerA().getScissorMonster().setCanUse(true);
        	
            this.frame.setEnabled(false);
            JOptionPane.showMessageDialog(frame, "Return to Choose Team Page.");
            this.frame.setVisible(false);
            this.chooseTeamForm.getFrame().setVisible(true);
        }
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(9, 12, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.setMaximumSize(new Dimension(558, 430));
        panelMain.setMinimumSize(new Dimension(558, 430));
        panelMain.setOpaque(false);
        panelMain.setPreferredSize(new Dimension(558, 430));
        panelMain.setToolTipText("");
        buttonPaper = new JButton();
        buttonPaper.setName("Paper");
        buttonPaper.setText("Paper");
        panelMain.add(buttonPaper, new GridConstraints(5, 5, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(120, 40), new Dimension(120, 40), new Dimension(120, 40), 0, false));
        checkBoxReverse = new JCheckBox();
        checkBoxReverse.setText("Use Reverse");
        panelMain.add(checkBoxReverse, new GridConstraints(6, 2, 1, 8, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonFight = new JButton();
        buttonFight.setText("Go!");
        panelMain.add(buttonFight, new GridConstraints(7, 0, 1, 12, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 30), new Dimension(100, 30), new Dimension(100, 30), 0, false));
        textAreaOutput = new JTextArea();
        textAreaOutput.setAutoscrolls(false);
        textAreaOutput.setEditable(false);
        textAreaOutput.setLineWrap(true);
        textAreaOutput.setText("");
        panelMain.add(textAreaOutput, new GridConstraints(4, 0, 1, 12, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(410, 80), new Dimension(410, 80), new Dimension(410, 80), 0, false));
        tableStatsEnemy.setAutoCreateRowSorter(false);
        tableStatsEnemy.setEnabled(false);
        panelMain.add(tableStatsEnemy, new GridConstraints(2, 6, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(150, 65), new Dimension(150, 65), new Dimension(150, 65), 0, false));
        tableStatsPlayer.setAutoResizeMode(2);
        tableStatsPlayer.setEnabled(false);
        panelMain.add(tableStatsPlayer, new GridConstraints(2, 2, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(150, 65), new Dimension(150, 65), new Dimension(150, 65), 0, false));
        labelStatsMonster = new JLabel();
        labelStatsMonster.setText("Player's Monsters Stats");
        panelMain.add(labelStatsMonster, new GridConstraints(1, 2, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelStatsEnemy = new JLabel();
        labelStatsEnemy.setText("Enemy's Monsters Stats");
        panelMain.add(labelStatsEnemy, new GridConstraints(1, 6, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonRock = new JButton();
        buttonRock.setName("Rock");
        buttonRock.setText("Rock");
        panelMain.add(buttonRock, new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(120, 40), new Dimension(120, 40), new Dimension(120, 40), 0, false));
        buttonScissor = new JButton();
        buttonScissor.setName("Scissor");
        buttonScissor.setText("Scissor");
        panelMain.add(buttonScissor, new GridConstraints(5, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(120, 40), new Dimension(120, 40), new Dimension(120, 40), 0, false));
        final Spacer spacer1 = new Spacer();
        panelMain.add(spacer1, new GridConstraints(5, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panelMain.add(spacer2, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panelMain.add(spacer3, new GridConstraints(0, 0, 1, 12, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 30), null, 0, false));
        final Spacer spacer4 = new Spacer();
        panelMain.add(spacer4, new GridConstraints(8, 0, 1, 12, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 30), null, 0, false));
        final Spacer spacer5 = new Spacer();
        panelMain.add(spacer5, new GridConstraints(1, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(70, -1), null, 0, false));
        final Spacer spacer6 = new Spacer();
        panelMain.add(spacer6, new GridConstraints(1, 10, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(70, -1), null, 0, false));
        progressBarPlayerLP = new JProgressBar();
        progressBarPlayerLP.setForeground(new Color(-13195743));
        progressBarPlayerLP.setIndeterminate(false);
        progressBarPlayerLP.setOrientation(0);
        progressBarPlayerLP.setString("Life Points");
        progressBarPlayerLP.setStringPainted(true);
        progressBarPlayerLP.setValue(100);
        panelMain.add(progressBarPlayerLP, new GridConstraints(3, 2, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(115, -1), new Dimension(115, -1), new Dimension(115, -1), 0, false));
        progressBarEnemyLP = new JProgressBar();
        progressBarEnemyLP.setForeground(new Color(-13195743));
        progressBarEnemyLP.setIndeterminate(false);
        progressBarEnemyLP.setString("Life Points");
        progressBarEnemyLP.setStringPainted(true);
        progressBarEnemyLP.setToolTipText("");
        progressBarEnemyLP.setValue(100);
        panelMain.add(progressBarEnemyLP, new GridConstraints(3, 6, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(115, -1), new Dimension(115, -1), new Dimension(115, -1), 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }
}