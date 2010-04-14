/*
 * TranslatePanel.java
 * Copyright (C) 2004-2005, Bjorn Bringert (bringert@cs.chalmers.se)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package se.chalmers.cs.gf.translategui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collection;
import java.util.ArrayList;

import se.chalmers.cs.gf.translate.Translator;
import se.chalmers.cs.gf.util.Pair;
import static se.chalmers.cs.gf.util.Pair.pair;

/**
 *  A translation widget with an input area, language selection
 *  and an output area.
 */
public class TranslatePanel extends JPanel {

        private Translator translator;

        private JTextField input;
        private JTextArea output;

        private JComboBox inputLang;
        private JComboBox outputLang;

        private JCheckBox preview;

        public TranslatePanel(Translator translator) {
                this.translator = translator;
                buildGUI();
        }

        private void buildGUI() {
                setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));

                input = new JTextField();
                input.getDocument().addDocumentListener(new InputChangeListener());
                input.addActionListener(new TranslateListener());

                output = new JTextArea(10, 40);
                output.setEditable(false);

                JScrollPane outputScroll = new JScrollPane(output, 
                                                           JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                           JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                

                inputLang = new JComboBox();
		inputLang.addItem("*all*");
                for (String l : translator.listParsers())
                        inputLang.addItem(l);
                inputLang.addActionListener(new TranslateListener());

                outputLang = new JComboBox();
		outputLang.addItem("*all*");
                for (String l : translator.listLinearizers())
                        outputLang.addItem(l);
                outputLang.addActionListener(new TranslateListener());

                JButton transButt = new JButton("Translate!");
                transButt.addActionListener(new TranslateListener());

                preview = new JCheckBox("preview", false);
                preview.addActionListener(new TranslateListener());

                // the center pane with language selections etc.
                JPanel controlPane = new JPanel();
                controlPane.setLayout(new FlowLayout());
                controlPane.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.black));
                controlPane.add(new JLabel("Translate from "));
                controlPane.add(inputLang);
                controlPane.add(new JLabel(" to "));
                controlPane.add(outputLang);
                controlPane.add(transButt);
                controlPane.add(preview);

                JPanel inputPane = new JPanel(new BorderLayout());
                inputPane.add(input, BorderLayout.CENTER);
                inputPane.add(controlPane, BorderLayout.SOUTH);

                setLayout(new BorderLayout());
                add(inputPane, BorderLayout.NORTH);
                add(outputScroll, BorderLayout.CENTER);
        }


        private void doTranslate() {
                String fromLang = (String)inputLang.getSelectedItem();
                String toLang = (String)outputLang.getSelectedItem();
                String inputText = input.getText();
		Collection<String> outputs = new ArrayList<String>();

		if (fromLang.equals("*all*") && toLang.equals("*all*")) {
			for (Pair<Pair<String,String>,String> p : 
				     translator.translateFromAllToAll(inputText)) {
				outputs.add(p.fst.fst+"=>"+p.fst.snd+": "+p.snd);
			}
		} else if (fromLang.equals("*all*")) {
			for (Pair<String,String> p : 
				     translator.translateFromAll(toLang, inputText)) {
				outputs.add(p.fst+": "+p.snd);
			}
		} else if (toLang.equals("*all*")) {
			for (Pair<String,String> p : 
				     translator.translateToAll(fromLang, inputText)) {
				outputs.add(p.fst+": "+p.snd);
			}
		} else {
			outputs.addAll(translator.translate(fromLang, toLang, inputText));
		}

                if (outputs.size() == 0) { // FIXME: handle these better
                        output.setText("<no parse>");
                } else {
			StringBuilder sb = new StringBuilder();
			for (String o : outputs)
				sb.append(o).append('\n');
                        output.setText(sb.toString());
                }
        }

	private <A> void showOutputs(Collection<A> outputs) {
                if (outputs.size() == 0) { // FIXME: handle these better
                        output.setText("<no parse>");
                } else {
			StringBuilder sb = new StringBuilder();
			for (A o : outputs)
				sb.append(o).append('\n');
                        output.setText(sb.toString());
                }
	}

        private void doPreview() {
                if (preview.isSelected())
                        doTranslate();
        }

        private class TranslateListener implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                        if (e.getSource() != preview || preview.isSelected())
                                doTranslate();
                }
        }

        private class InputChangeListener implements DocumentListener {
                public void changedUpdate(DocumentEvent e) { }
                public void insertUpdate(DocumentEvent e) { 
                        doPreview();
                }
                public void removeUpdate(DocumentEvent e) { 
                        doPreview();
                }
        }

}
