package fr.pv.mushtool;



import static fr.pv.mushtool.Utils.getPreviousIndexArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PolarChartVr extends View implements View.OnTouchListener {

    public static final String TAG = "PolarChartVR";

    private PolarChartVr.PolarChartListener polarChartListener;

    private int nbSections = 0;
    private int nbCircles = 0;
    private int maxSpeedScale = 20;


    private float density;
    private float radius;
    private int chartWidth, chartHeight;
    private float padding = 0;
    private ArrayList<Path> sectionsPath = new ArrayList<>();
    private float currentAngle, currentSectionX, currentSectionY;


    //pour tracer les voiles
    private Path graph = new Path();
    private ArrayList<Float> sectionsValue = new ArrayList<>();
    private ArrayList<Integer> sectionsAngle = new ArrayList<>();
    private int currentPathX, currentPathY;



    //
    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint sectionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint radiusTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint shapePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int scaledSize = getResources().getDimensionPixelSize(R.dimen.myFontSize);

    /**Constructor**/
    public PolarChartVr(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        chartWidth = w;
        chartHeight = h;


        if (chartWidth > chartHeight/2) {
            radius = chartHeight/2 * nbCircles * 1f / (nbCircles *1f) - padding;
        } else {
            radius = chartWidth * nbCircles * 1f / (nbCircles *1f) - padding;
        }


        createSectionsPath();
        createGraphPath();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw values
        canvas.drawPath(graph, shapePaint);

        //Draw Legends radius
        radiusTextPaint.setTextSize(scaledSize);
        for (int i = 0; i <= (nbCircles); i++) {
            canvas.drawText(String.valueOf((i)*maxSpeedScale/(nbCircles)), padding/2, chartHeight / 2 -radius*(i)/nbCircles, radiusTextPaint);
        }


        // Draw arccircles
        for (int i = 1; i <= nbCircles; i++) {
            canvas.drawArc((float) (-radius+padding), (float) (0 ) +padding, (float) (radius+ padding), (float) ((radius)*2)+padding,270,180, true, circlePaint);
            canvas.drawArc((float) (-radius +radius*i/nbCircles -radius/nbCircles+padding), (float) (0 +radius*i/nbCircles -radius/nbCircles ) +padding, (float) (radius -radius*i/nbCircles + radius/nbCircles+ padding), (float) ((radius )*2)-radius*i/nbCircles +radius/nbCircles+padding,270,180, true, circlePaint);
        }

        // Draw sectionsPath
        for (Path sectionPath : sectionsPath) {
            canvas.drawPath(sectionPath, sectionPaint);
            canvas.drawTextOnPath(String.valueOf(sectionsPath.indexOf(sectionPath)*180/(nbSections-1)), sectionPath, radius-50, 0, radiusTextPaint);

        }
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        manageTouch(event);
        return true;
    }


    private void init(Context context, AttributeSet attrs) {
        if (!isInEditMode() && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PolarChartVr);
            nbSections = a.getInt(R.styleable.PolarChartVr_nb_sections, 17);
            nbCircles = a.getInt(R.styleable.PolarChartVr_nb_circles, 5);
        }

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        density = metrics.density;
        padding = 20 * density;

        setBackgroundColor(Color.TRANSPARENT);

        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.parseColor("#212121"));
        circlePaint.setStrokeWidth(1);
        circlePaint.setPathEffect(new DashPathEffect(new float[]{3, 2}, 2));

        sectionPaint.setStyle(Paint.Style.STROKE);
        sectionPaint.setColor(Color.parseColor("#212121"));
        sectionPaint.setStrokeWidth(1);
        sectionPaint.setPathEffect(new DashPathEffect(new float[]{3, 2}, 2));

        shapePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        shapePaint.setColor(Color.parseColor("#2196F3"));





    }

    /**
     * Create sections path
     */
    private void createSectionsPath() {

        sectionsPath.clear();

        for (int i = 0; i < nbSections; i++) {
            currentAngle = -90 + (i * 1f / (nbSections-1) * 180);
            float centerX = (float) ((padding ) + Math.cos(currentAngle * Math.PI / 180));
            float centerY = (float) ((chartHeight / 2) + Math.sin(currentAngle * Math.PI / 180));
            currentSectionX = (float) ((padding) + radius * Math.cos(currentAngle * Math.PI / 180));
            currentSectionY = (float) ((chartHeight/2) + radius * Math.sin(currentAngle * Math.PI / 180));
            Path path = new Path();
            path.moveTo(centerX, centerY);
            path.lineTo(currentSectionX, currentSectionY);
            sectionsPath.add(path);


        }
    }

    /**
     * Create graph path
     */
    private void createGraphPath() {

        if (sectionsValue.size() == 0) {
            return;
        }

        graph.reset();

        for (int i = 0; i < sectionsValue.size(); i++) {
            currentAngle = -90 + (i/2 * 1f / (nbSections-1) * 180);

            currentPathX = (int) ((padding) + (radius * sectionsValue.get(i) * 1f / nbCircles)
                    * Math.cos(currentAngle * Math.PI / 180));
            currentPathY = (int) ((chartHeight / 2) + (radius * sectionsValue.get(i) * 1f / nbCircles)
                    * Math.sin(currentAngle * Math.PI / 180));
            if (i == 0) {
                graph.moveTo(currentPathX, currentPathY);
            } else {
                graph.lineTo(currentPathX, currentPathY);
            }
        }

        /*currentAngle = -90;
        currentPathX = (int) ((chartWidth / 2) + (radius * sectionsValue.get(0) * 1f / nbCircles)
                * Math.cos(currentAngle * Math.PI / 180));
        currentPathY = (int) ((chartHeight / 2) + (radius * sectionsValue.get(0) * 1f / nbCircles)
                * Math.sin(currentAngle * Math.PI / 180));
        graph.lineTo(currentPathX, currentPathY);*/
        graph.close();


    }



    private void manageTouch(MotionEvent event) {

    }


    /**
     * Set the number of sectionsPath
     */
    public void setNbSections(int nbSections) {
        this.nbSections = nbSections;
    }

    /**
     * Set the number of circles
     */
    public void setNbCircles(int nbCircles) {
        this.nbCircles = nbCircles;
    }

    public float setMaxScale(float maxSpeed){
        float min = 5;
        float max  = 50;
        float maxScale = 0;

        if (maxSpeed< min){
            maxScale = min;
        }
        else if (maxSpeed> max){
            maxScale = max;
        }
        else{
            for (int i = 1;maxSpeed > (i * min); i++ ){
                maxScale = (i+1)*min;
            }
        }


        return maxScale;

    }


    /**
     * The the polar chart listener
     */
    public PolarChartVr.PolarChartListener getPolarChartListener() {
        return polarChartListener;
    }

    /**
     * Set the polar chart listener
     *
     * @param polarChartListener : if null, the listener is removed
     */
    public void setPolarChartListener(PolarChartVr.PolarChartListener polarChartListener) {
        this.polarChartListener = polarChartListener;
        if (polarChartListener == null) {
            setOnTouchListener(null);
        } else {
            setOnTouchListener(this);
        }
    }

    /**
     * Interface
     */
    public interface PolarChartListener {
        void onValueChanged(int section, float value);

    }


    /**
     * Set the sectionsPath value
     */
    public void setJib(ArrayList<Integer> TwaArray, ArrayList<Float> JibSpeed) throws Exception {
    int index_before =0;
    int index_after =0;
    float interpolated_value =0;
    for (int i = 0; i<=180; i++){
        Log.d(TAG, "i" + i);
        index_before = getPreviousIndexArrayList(TwaArray,i);
        if(index_before<TwaArray.size()-1){
            index_after = index_before +1;
        }else {
            index_after =index_before;
        }
        //Log.d(TAG, "index" + index_before);
        //sectionsValue.add();
        }
        sectionsAngle = TwaArray;
        sectionsValue = JibSpeed;
        Log.d(TAG, "sections values" + sectionsValue);
        createGraphPath();
        invalidate();

    }


    public void setSectionsValue(final ArrayList<Float> newSectionsValue, final ArrayList<Integer> newTwaValue) {

        /*if (newSectionsValue.size() != nbSections) {
            Log.e(TAG, "The number of values isn't equal to the number of sections");
            return;
        }*/

        /*for (int i = 0; i < newSectionsValue.size(); i++) {
            float value = newSectionsValue.get(i);
            value = Math.min(Math.max(value, 0), nbCircles);
            newSectionsValue.set(i, value);
        }*/

        //sectionsValue.clear();
        //sectionsValue.addAll(newSectionsValue);
        //Log.d(TAG, "sections values" + sectionsValue);
        //createGraphPath();
        //invalidate();
    }



}