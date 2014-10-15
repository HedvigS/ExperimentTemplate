/*
 * Copyright (C) 2014 Language In Interaction
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.ru.languageininteraction.synaesthesia.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CanvasGradient;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @since Oct 8, 2014 5:09:10 PM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public class ColourPickerCanvasView extends AbstractView {

    private final Canvas mainCanvas;
    private final Canvas hueCanvas;
    private final Canvas luminanceCanvas;
    private final Grid innerGrid;
    private final Grid pickerPanel;
    private final VerticalPanel controlsPanel;
    private final HorizontalPanel buttonPanel;
    private final VerticalPanel stimulusPanel;
    private final Label instructionsLabel;
    private final Label progressLabel;
    private final int height;
    private final int width;
    private final int barWidth;
    private final int selectedColourPanelSize;

    public ColourPickerCanvasView() {

        final int clientHeight = Window.getClientHeight();
        final int clientWidth = Window.getClientWidth();
        final int minClient = (clientHeight > clientWidth) ? clientWidth : clientHeight;
        height = (int) (minClient * 0.5);
        width = (int) (minClient * 0.5);
        barWidth = (int) (minClient * 0.05);
        selectedColourPanelSize = (int) (minClient * 0.1);

        buttonPanel = new HorizontalPanel();
        stimulusPanel = new VerticalPanel();
        innerGrid = new Grid(2, 2);
        pickerPanel = new Grid(2, 2);
        controlsPanel = new VerticalPanel();
        instructionsLabel = new Label();
        progressLabel = new Label();
        mainCanvas = Canvas.createIfSupported();
        hueCanvas = Canvas.createIfSupported();
        luminanceCanvas = Canvas.createIfSupported();

        if (mainCanvas == null || hueCanvas == null || luminanceCanvas == null) {
            add(new Label("canvas is unsupported"));
            return;
        } else {
            mainCanvas.setCoordinateSpaceHeight(height);
            mainCanvas.setCoordinateSpaceWidth(width);
            mainCanvas.setSize(width + "px", height + "px");
            hueCanvas.setCoordinateSpaceHeight(height);
            hueCanvas.setCoordinateSpaceWidth(barWidth);
            hueCanvas.setSize(barWidth + "px", height + "px");
            luminanceCanvas.setCoordinateSpaceHeight(barWidth);
            luminanceCanvas.setCoordinateSpaceWidth(width);
            luminanceCanvas.setSize(width + "px", barWidth + "px");
            final Context2d hueContext2d = hueCanvas.getContext2d();
            final Context2d luminanceContext2d = luminanceCanvas.getContext2d();

            CanvasGradient hueGradient = hueContext2d.createLinearGradient(0, 0, 0, height);
            for (int stop = 0; stop <= 10; stop++) {
                hueGradient.addColorStop(stop * 0.1f, "hsl(" + 36 * stop + ",100%,50%);");
            }
            hueContext2d.setFillStyle(hueGradient);
            hueContext2d.fillRect(0, 0, barWidth, height);

            CanvasGradient luminanceGradient = luminanceContext2d.createLinearGradient(0, 0, width, 0);
            luminanceGradient.addColorStop(0f, "white");
            luminanceGradient.addColorStop(1f, "black");
            luminanceContext2d.setFillStyle(luminanceGradient);
            luminanceContext2d.fillRect(0, 0, width, barWidth);

            pickerPanel.setCellPadding(5);
            pickerPanel.setWidget(0, 0, mainCanvas);
            pickerPanel.setWidget(0, 1, hueCanvas);
            pickerPanel.setWidget(1, 0, luminanceCanvas);
            final Label selectedColourLabel = new Label("SelectedColour");
            selectedColourLabel.setHeight(100 + "px");
            selectedColourLabel.setWidth(100 + "px");
            final VerticalPanel selectedColourPanel = new VerticalPanel();
            selectedColourPanel.add(selectedColourLabel);
            controlsPanel.add(selectedColourPanel);
//            final Label hoverColourLabel = new Label("HoverColour");
//            hoverColourLabel.setHeight(selectedColourPanelSize + "px");
//            hoverColourLabel.setWidth(selectedColourPanelSize + "px");
//            final VerticalPanel hoverColourPanel = new VerticalPanel();
//            hoverColourPanel.add(hoverColourLabel);
//            controlsPanel.add(hoverColourPanel);
            controlsPanel.add(buttonPanel);
            stimulusPanel.setHeight(selectedColourPanelSize + "px");
            stimulusPanel.setWidth(selectedColourPanelSize + "px");
            controlsPanel.add(stimulusPanel);
            controlsPanel.add(progressLabel);
//            mainCanvas.addMouseMoveHandler(new MouseMoveHandler() {
//
//                @Override
//                public void onMouseMove(MouseMoveEvent event) {
//                    setColour(event, mainCanvas, hoverColourPanel);
//                }
//            });
//            luminanceCanvas.addMouseMoveHandler(new MouseMoveHandler() {
//
//                @Override
//                public void onMouseMove(MouseMoveEvent event) {
//                    setColour(event, luminanceCanvas, hoverColourPanel);
//                }
//            });

            mainCanvas.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    setColour(event, mainCanvas, selectedColourPanel);
                }
            });
            luminanceCanvas.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    setColour(event, luminanceCanvas, selectedColourPanel);
                }
            });
            hueCanvas.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    setHue(event, hueCanvas);
                }
            });
//            mainCanvas.addTouchMoveHandler(new HandlesAllTouchEvents() {
//
//                @Override
//                public void onTouchStart(TouchStartEvent event) {
//                }
//
//                @Override
//                public void onTouchMove(TouchMoveEvent event) {
//                    setColour(event, mainCanvas, hoverColourLabel);
//                }
//
//                @Override
//                public void onTouchEnd(TouchEndEvent event) {
//                    setColour(event, mainCanvas, selectedColourLabel);
//                }
//
//                @Override
//                public void onTouchCancel(TouchCancelEvent event) {
//                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                }
//            });
        }
        innerGrid.setWidget(0, 0, pickerPanel);
        Grid outerGrid = new Grid(2, 1);
        outerGrid.setWidget(0, 0, innerGrid);
        outerGrid.setWidget(1, 0, instructionsLabel);
        final ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.add(outerGrid);
        add(scrollPanel);
    }

    protected void setRandomColour() {
        setHue(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255));
    }

    private void setHue(int red, int green, int blue) {
        final Context2d mainContext2d = mainCanvas.getContext2d();
        CanvasGradient linearColour = mainContext2d.createLinearGradient(0, 0, width, 0);
        linearColour.addColorStop(1f, "white");
//        linearColour.addColorStop(0f, "hsl(" + hue + ",100%,50%);");
        linearColour.addColorStop(0f, "rgb(" + red + "," + green + "," + blue + ")");
        mainContext2d.setFillStyle(linearColour);
        mainContext2d.fillRect(0, 0, width, height);

        CanvasGradient linearGrey = mainContext2d.createLinearGradient(0, 0, 0, height);
        linearGrey.addColorStop(1f, "black");
        linearGrey.addColorStop(0f, "rgba(0,0,0,0);");
        mainContext2d.setFillStyle(linearGrey);
        mainContext2d.fillRect(0, 0, width, height);
    }

    private void setColour(MouseEvent event, Canvas targetCanvas, VerticalPanel targetPanel) {
        setColour(event.getRelativeX(targetCanvas.getElement()), event.getRelativeY(targetCanvas.getElement()), targetCanvas, targetPanel);
    }

//    private void setColour(TouchEvent event, Canvas targetCanvas, Label targetLabel) {
//        if (event.getTouches().length() > 0) {
//          Touch touch = event.getTouches().get(0);
//        setColour(event.getRelativeX(targetCanvas.getElement()), event.getRelativeY(targetCanvas.getElement()), targetCanvas, targetLabel);
//        }
//    }
    private void setColour(int x, int y, Canvas targetCanvas, VerticalPanel targetPanel) {
        final ImageData imageData = targetCanvas.getContext2d().getImageData(x, y, 1, 1);
        final int blue = imageData.getBlueAt(0, 0);
        final int green = imageData.getGreenAt(0, 0);
        final int red = imageData.getRedAt(0, 0);
        targetPanel.getElement().setAttribute("style", "background:rgb(" + red + "," + green + "," + blue + ")");
    }

    private void setHue(MouseEvent event, Canvas targetCanvas) {
        setHue(event.getRelativeX(targetCanvas.getElement()), event.getRelativeY(targetCanvas.getElement()), targetCanvas);
    }

    private void setHue(int x, int y, Canvas targetCanvas) {
        final ImageData imageData = targetCanvas.getContext2d().getImageData(x, y, 1, 1);
        final int blue = imageData.getBlueAt(0, 0);
        final int green = imageData.getGreenAt(0, 0);
        final int red = imageData.getRedAt(0, 0);
        setHue(red, green, blue);
    }

    protected void setInstructions(String instructions) {
        instructionsLabel.setText(instructions);
    }

    protected void setStimulus(String stimulus, String progress) {
        progressLabel.setText(progress);
        stimulusPanel.clear();
        stimulusPanel.add(new Label(stimulus));
    }

    protected void setButton(String buttonText, final AppEventListner presenterListerner) {
        final Button nextButton = new Button(buttonText);
        nextButton.addStyleName("nextButton");
        nextButton.setEnabled(true);
        buttonPanel.add(nextButton);
        nextButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                presenterListerner.eventFired();
            }
        });
    }

    @Override
    protected void parentResized(int height, int width, String units) {
        if (height < width) {
            innerGrid.setWidget(0, 1, controlsPanel);
        } else {
            innerGrid.setWidget(1, 0, controlsPanel);
        }
    }
}
