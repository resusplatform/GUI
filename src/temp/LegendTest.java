package temp;
//package test;
//
//public class MyLegendItem implements GraphLegendItem, LegendItemSource {
//
//    LegendItemCollection legendItemCollection;
//    
//    public MyhLegendItem(final String theLabel, final Color fillColor) {
//        legendItemCollection = new LegendItemCollection();
//        
//        //a shape is made ready basing on the fillcolor
//        boolean shapeIsVisible = true;
//        Shape shape = new Rectangle(12,12);
//        boolean shapeIsFilled = true;
//        Paint fillPaint = fillColor;
//        boolean shapeOutlineVisible = true;
//        Paint outlinePaint = Color.BLACK;
//        Stroke outlineStroke = new BasicStroke();
//        boolean lineVisible = false;
//        Stroke lineStroke = new BasicStroke();
//        Paint linePaint = Color.BLACK;
//        LegendItem result = new LegendItem(theLabel, theLabel, null,
//                null, shapeIsVisible, shape, shapeIsFilled,
//                fillPaint, shapeOutlineVisible, outlinePaint,
//                outlineStroke, lineVisible, new Line2D.Double(-7.0, 0.0, 7.0, 0.0),
//                lineStroke, linePaint);
//        
//        legendItemCollection.add(result);
//    }
//
//    public LegendItemCollection getLegendItems() {
//        return legendItemCollection;
//    }
//}