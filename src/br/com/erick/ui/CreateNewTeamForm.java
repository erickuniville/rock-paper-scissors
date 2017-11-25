package br.com.erick.ui;

import br.com.erick.constants.Constants;
import br.com.erick.dao.MonsterDAO;
import br.com.erick.dao.TeamDAO;
import br.com.erick.model.Monster;
import br.com.erick.model.Team;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

import static br.com.erick.constants.Constants.PAPER;
import static br.com.erick.constants.Constants.ROCK;
import static br.com.erick.constants.Constants.SCISSORS;

/**
 * Created by erick.budal on 12/11/2017.
 */
public class CreateNewTeamForm {
    private JPanel panelCreateNewTeam;
    private JLabel labelRock;
    private JLabel labelScissors;
    private JLabel labelPaper;
    private JTextField textFieldRockAttack;
    private JTextField textFieldPaperAttack;
    private JTextField textFieldScissorsAttack;
    private JTextField textFieldRockDefense;
    private JTextField textFieldPaperDefense;
    private JTextField textFieldScissorsDefense;
    private JLabel labelRockAttack;
    private JLabel labelRockDefense;
    private JLabel labelPaperAttack;
    private JLabel labelPaperDefense;
    private JLabel labelScissorsAttack;
    private JLabel labelScissorsDefense;
    private JButton buttonCreateTeam;
    private JTextField textFieldTeamName;
    private JLabel labelTeamName;
    private JFrame frame;
    private ChooseTeamForm chooseTeamForm;

    public CreateNewTeamForm(ChooseTeamForm chooseTeamForm) {
        frame = new JFrame("CreateNewTeamForm");
        frame.setContentPane(panelCreateNewTeam);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

        textFieldRockAttack.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                typedSomething(textFieldRockAttack, Constants.ROCK_ATTACK_FIELD);
            }
        });

        textFieldPaperAttack.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                typedSomething(textFieldPaperAttack, Constants.PAPER_ATTACK_FIELD);
            }
        });

        textFieldScissorsAttack.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyTyped(e);
                typedSomething(textFieldScissorsAttack, Constants.SCISSORS_ATTACK_FIELD);
            }
        });

        textFieldRockDefense.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                typedSomething(textFieldRockDefense, Constants.ROCK_DEFENSE_FIELD);
            }
        });

        textFieldPaperDefense.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                typedSomething(textFieldPaperDefense, Constants.PAPER_DEFENSE_FIELD);
            }
        });

        textFieldScissorsDefense.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyTyped(e);
                typedSomething(textFieldScissorsDefense, Constants.SCISSORS_DEFENSE_FIELD);
            }
        });
        buttonCreateTeam.addActionListener(e -> createTeamButtonClicked());

        this.chooseTeamForm = chooseTeamForm;
    }

    public void typedSomething(JTextField field, int whichField) {

        int numberTyped = Integer.MIN_VALUE;

        try {
            numberTyped = Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            field.setText(null);
            return;
        }

        if (numberTyped >= 1 && numberTyped <= 99) {
            switch (whichField) {
                case Constants.ROCK_ATTACK_FIELD:
                    textFieldRockDefense.setText(Integer.toString(100 - numberTyped));
                    break;
                case Constants.PAPER_ATTACK_FIELD:
                    textFieldPaperDefense.setText(Integer.toString(100 - numberTyped));
                    break;
                case Constants.SCISSORS_ATTACK_FIELD:
                    textFieldScissorsDefense.setText(Integer.toString(100 - numberTyped));
                    break;
                case Constants.ROCK_DEFENSE_FIELD:
                    textFieldRockAttack.setText(Integer.toString(100 - numberTyped));
                    break;
                case Constants.PAPER_DEFENSE_FIELD:
                    textFieldPaperAttack.setText(Integer.toString(100 - numberTyped));
                    break;
                case Constants.SCISSORS_DEFENSE_FIELD:
                    textFieldScissorsAttack.setText(Integer.toString(100 - numberTyped));
                    break;
            }
        } else {
            if (whichField == Constants.ROCK_ATTACK_FIELD || whichField == Constants.ROCK_DEFENSE_FIELD) {
                textFieldRockAttack.setText(null);
                textFieldRockDefense.setText(null);
            } else if (whichField == Constants.PAPER_ATTACK_FIELD || whichField == Constants.PAPER_DEFENSE_FIELD) {
                textFieldPaperAttack.setText(null);
                textFieldPaperDefense.setText(null);
            } else {
                textFieldScissorsAttack.setText(null);
                textFieldScissorsDefense.setText(null);
            }
        }
    }

    private void createTeamButtonClicked() {
        TeamDAO teamDAO = new TeamDAO();
        int teamId = teamDAO.insertTeam(textFieldTeamName.getText());

        int rockAttack = Integer.parseInt(textFieldRockAttack.getText());
        int rockDefense = Integer.parseInt(textFieldRockDefense.getText());
        int paperAttack = Integer.parseInt(textFieldPaperAttack.getText());
        int paperDefense = Integer.parseInt(textFieldPaperDefense.getText());
        int scissorsAttack = Integer.parseInt(textFieldScissorsAttack.getText());
        int scissorsDefense = Integer.parseInt(textFieldScissorsDefense.getText());

        Monster rockMonster = new Monster(rockAttack, rockDefense, ROCK);
        Monster paperMonster = new Monster(paperAttack, paperDefense, PAPER);
        Monster scissorsMonster = new Monster(scissorsAttack, scissorsDefense, SCISSORS);

        MonsterDAO monsterDAO = new MonsterDAO();
        monsterDAO.insertTeamMonsters(rockMonster, paperMonster, scissorsMonster, teamId);

        frame.setVisible(false);

        DefaultComboBoxModel comboBoxModel = (DefaultComboBoxModel) this.chooseTeamForm.getComboBoxTeams().getModel();
        Team team = new Team(teamId, textFieldTeamName.getText(), rockMonster, paperMonster, scissorsMonster);
        comboBoxModel.addElement(team);
        this.chooseTeamForm.getComboBoxTeams().setModel(comboBoxModel);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelCreateNewTeam = new JPanel();
        panelCreateNewTeam.setLayout(new GridLayoutManager(11, 9, new Insets(0, 0, 0, 0), -1, -1));
        panelCreateNewTeam.setMaximumSize(new Dimension(558, 430));
        panelCreateNewTeam.setMinimumSize(new Dimension(558, 430));
        final Spacer spacer1 = new Spacer();
        panelCreateNewTeam.add(spacer1, new GridConstraints(9, 1, 2, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 5), new Dimension(-1, 5), new Dimension(-1, 5), 0, false));
        final Spacer spacer2 = new Spacer();
        panelCreateNewTeam.add(spacer2, new GridConstraints(1, 0, 10, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panelCreateNewTeam.add(spacer3, new GridConstraints(1, 8, 10, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        labelRock = new JLabel();
        labelRock.setText("Rock Monster");
        panelCreateNewTeam.add(labelRock, new GridConstraints(1, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelPaper = new JLabel();
        labelPaper.setText("Paper Monster");
        panelCreateNewTeam.add(labelPaper, new GridConstraints(1, 4, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelScissors = new JLabel();
        labelScissors.setText("Scissors Monster");
        panelCreateNewTeam.add(labelScissors, new GridConstraints(1, 6, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelRockAttack = new JLabel();
        labelRockAttack.setText("Attack: ");
        panelCreateNewTeam.add(labelRockAttack, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelRockDefense = new JLabel();
        labelRockDefense.setText("Defense: ");
        panelCreateNewTeam.add(labelRockDefense, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldRockAttack = new JTextField();
        panelCreateNewTeam.add(textFieldRockAttack, new GridConstraints(3, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(40, -1), new Dimension(40, -1), new Dimension(40, -1), 0, false));
        textFieldRockDefense = new JTextField();
        panelCreateNewTeam.add(textFieldRockDefense, new GridConstraints(4, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(40, -1), new Dimension(40, -1), new Dimension(40, -1), 0, false));
        labelPaperAttack = new JLabel();
        labelPaperAttack.setText("Attack:");
        panelCreateNewTeam.add(labelPaperAttack, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelPaperDefense = new JLabel();
        labelPaperDefense.setText("Defense: ");
        panelCreateNewTeam.add(labelPaperDefense, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldPaperAttack = new JTextField();
        panelCreateNewTeam.add(textFieldPaperAttack, new GridConstraints(3, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(40, -1), new Dimension(40, -1), new Dimension(40, -1), 0, false));
        textFieldPaperDefense = new JTextField();
        panelCreateNewTeam.add(textFieldPaperDefense, new GridConstraints(4, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(40, -1), new Dimension(40, -1), new Dimension(40, -1), 0, false));
        labelScissorsAttack = new JLabel();
        labelScissorsAttack.setText("Attack: ");
        panelCreateNewTeam.add(labelScissorsAttack, new GridConstraints(3, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelScissorsDefense = new JLabel();
        labelScissorsDefense.setText("Defense: ");
        panelCreateNewTeam.add(labelScissorsDefense, new GridConstraints(4, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldScissorsAttack = new JTextField();
        panelCreateNewTeam.add(textFieldScissorsAttack, new GridConstraints(3, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(40, -1), new Dimension(40, -1), new Dimension(40, -1), 0, false));
        textFieldScissorsDefense = new JTextField();
        panelCreateNewTeam.add(textFieldScissorsDefense, new GridConstraints(4, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(40, -1), new Dimension(40, -1), new Dimension(40, -1), 0, false));
        buttonCreateTeam = new JButton();
        buttonCreateTeam.setText("Create new team");
        panelCreateNewTeam.add(buttonCreateTeam, new GridConstraints(8, 1, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(150, -1), new Dimension(150, -1), new Dimension(150, -1), 0, false));
        labelTeamName = new JLabel();
        labelTeamName.setText("Team Name:");
        panelCreateNewTeam.add(labelTeamName, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldTeamName = new JTextField();
        panelCreateNewTeam.add(textFieldTeamName, new GridConstraints(6, 2, 1, 6, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(260, -1), new Dimension(260, -1), new Dimension(260, -1), 0, false));
        final Spacer spacer4 = new Spacer();
        panelCreateNewTeam.add(spacer4, new GridConstraints(7, 1, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), new Dimension(-1, 10), new Dimension(-1, 30), 0, false));
        final Spacer spacer5 = new Spacer();
        panelCreateNewTeam.add(spacer5, new GridConstraints(5, 1, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), new Dimension(-1, 10), new Dimension(-1, 10), 0, false));
        final Spacer spacer6 = new Spacer();
        panelCreateNewTeam.add(spacer6, new GridConstraints(0, 0, 1, 9, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), new Dimension(-1, 10), new Dimension(-1, 10), 0, false));
        final Spacer spacer7 = new Spacer();
        panelCreateNewTeam.add(spacer7, new GridConstraints(2, 1, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), new Dimension(-1, 10), new Dimension(-1, 10), 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelCreateNewTeam;
    }
}
