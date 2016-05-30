package temp;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Slider;
//import org.jfree.chart.JFreeChart;
//import org.jfree.experimental.chart.swt.ChartComposite;
//
//public class MyExampleChartComposite extends ChartComposite {
//    // snip
//    /**
//     * A slider to choose the legend items to display
//     */
//     private Slider legendSlider;
//     /**
//     * Number of legend items to display on the chart
//     */
//     private final static int NUMBER_OF_LEGENDITEMS_TO_DISPLAY = 10;
//
//     private void createPartControl(Composite parent, int style) {
//         JFreeChart chart = createChart();
//         setChart(chart);
//         legendSlider = new Slider(parent, SWT.NONE|SWT.H_SCROLL);
//         legendSlider.addSelectionListener(new SelectionListener() {
//             @Override
//             public void widgetSelected(SelectionEvent arg0) {
//                      refreshLegend();
//             }
//         });
//         
//         
//     }
//     JFreeChart chart;
//     public void setChart(JFreeChart ch){
//    	 chart=ch;
//     }
//     private JFreeChart createChart() {
//    	 chart.addChangeListener(new ChartChangeListener() {    
//             @Override
//             public void chartChanged(ChartChangeEvent e) {
//                  if (e.getType().equals(ChartChangeEventType.DATASET_UPDATED)) {
//                      refreshLegend();
//                  }
//             }
//         });
//     
//     }
//
//    /**
//     * Refresh the the LegendItems and the slider,
//     * according to slider selection.
//     */
//    public void refreshLegend() {
//        // Display LegendItems according to the selection
//        // display the 10 nearest legend item (current selected element included)
//        int begin = legendSlider.getSelection() - (NUMBER_OF_LEGENDITEMS_TO_DISPLAY/2);
//        int end = legendSlider.getSelection() + (NUMBER_OF_LEGENDITEMS_TO_DISPLAY/2 -1);
//        int seriesEndIndex = Math.max(getSeriesCount()-1, 0);
//        // if begin is less than 0
//        // set it to 0, and increase end to display 10 items
//        if (begin < 0) {
//            begin = 0; 
//            end = NUMBER_OF_LEGENDITEMS_TO_DISPLAY - 1;
//        }
//        // if end is greater than the number of series plotted
//        // set it to the max possible value and increase begin to
//        // display 10 items
//        if  (end > seriesEndIndex) {
//            end = seriesEndIndex;  
//            begin = seriesEndIndex - (NUMBER_OF_LEGENDITEMS_TO_DISPLAY - 1);
//        }
//        end = Math.min(seriesEndIndex, end);
//        begin = Math.max(begin, 0);
//        // Refresh only if begin != end
//        if (end != begin) {
//            refreshLegendItems(begin, end);
//            refreshLegendSlider();
//        } else {
//            // in this case no more series are plotted on the chart
//            // clear legend
//            getChart().clearSubtitles();
//        }
//    }
//    /**
//     * Refresh the LegendTitle.
//     * Display only LegendItems between beginIndex and toIndex,
//     * to preserve space for the chart.
//     * @param beginIndex index of the {@link LegendItemCollection} used as the beginning of the new {@link LegendTitle}
//     * @param endIndex index of the {@link LegendItemCollection} used as the end of the new {@link LegendTitle}
//     */
//    private void refreshLegendItems(int beginIndex, int endIndex) {
//        // Last 10 items
//        final LegendItemCollection result = new LegendItemCollection();
//        // get the renderer to retrieve legend items
//        XYPlot plot = getChart().getXYPlot();
//        XYItemRenderer renderer = plot.getRenderer();
//        // Number of series displayed on the chart 
//        // Construct the legend
//        for (int i = beginIndex; i <= endIndex; i++) {
//            LegendItem item = renderer.getLegendItem(0, i);
//            result.add(item);
//        }
//        // Because the only way to create a new LegendTitle is to
//        // create a LegendItemSource first
//        LegendItemSource source = new LegendItemSource() {
//            LegendItemCollection lic = new LegendItemCollection();
//            {lic.addAll(result);}
//            public LegendItemCollection getLegendItems() {  
//                return lic;
//            }
//        };
//        // clear previous legend title
//        getChart().clearSubtitles();
//        // Create the new LegendTitle and set its position
//        LegendTitle legend = new LegendTitle(source);
//        legend.setHorizontalAlignment(HorizontalAlignment.CENTER);
//        legend.setVerticalAlignment(VerticalAlignment.CENTER);
//        legend.setPosition(RectangleEdge.BOTTOM);
//        legend.setBorder(1.0, 1.0, 1.0, 1.0);
//        legend.setVisible(true);
//        // Add the LegendTitle to the graph
//        getChart().addLegend(legend);
//    }
//
//    /**
//     * Set values of the slider according to the number of series
//     * plotted on the graph
//     */
//    private void refreshLegendSlider() {
//        int max = getSeriesCount() -1;
//        int selection = Math.min(legendSlider.getSelection(), max);
//        legendSlider.setValues(selection, 0, max, 1, 1, 1);
//    }
//}
