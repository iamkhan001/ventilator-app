package com.aafiyahtech.ventilator.ui.activities

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.aafiyahtech.ventilator.R
import com.aafiyahtech.ventilator.ui.viewModels.MainViewModel
import com.aafiyahtech.ventilator.utils.ApiCaller
import com.aafiyahtech.ventilator.utils.AppDataProvider
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_graph.*



class GraphActivity : AppCompatActivity(), OnChartValueSelectedListener {

    override fun onNothingSelected() {

        Log.i(TAG,"No Entry selected")

    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {

        Log.i(TAG,"Entry selected"+ e.toString())

    }

    companion object{
        private const val TAG = "GraphActivity"
        private var MAX_ENTRIES = 10
        private var MAXX = 10f

        const val actPressureMin = -10f
        const val actPressureMax = 50f

        const val actFlowMin = -100f
        const val actFlowMax = 100f

        const val actVolumeMin = 0f
        const val actVolumeMax = 1000f

    }

    private var showUpdates = true
    private lateinit var apiCaller: ApiCaller

    private  lateinit var tfRegular: Typeface
    private lateinit var tfLight: Typeface

    private lateinit var appDataProvider: AppDataProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        val model: MainViewModel by viewModels()
        model.init(this)

        imgBack.setOnClickListener {
            showUpdates = false
            onBackPressed()
        }

        model.mGroup6A.observe(this, Observer {

                if (it != null && showUpdates ) {
                    addEntryForGraph1(it.actPressure)
                    addEntryForGraph2(it.actFlow)
                    addEntryForGraph3(it.actVol)
                }

        })

        tfRegular = Typeface.createFromAsset(assets, "OpenSans-Regular.ttf")
        tfLight = Typeface.createFromAsset(assets, "OpenSans-Light.ttf")

        appDataProvider = AppDataProvider.getInstance(this.applicationContext)


        val seconds = appDataProvider.getGraphEntries()
        val delay = appDataProvider.getGraphUpdate()

        val rate = (seconds * 1000) / delay

        MAXX = rate.toFloat()
        MAX_ENTRIES = rate

        Log.e(TAG,"entries: ${appDataProvider.getGraphEntries()} delay: ${appDataProvider.getGraphUpdate()}")

        initGraph1()
        initGraph2()
        initGraph3()


        apiCaller = ApiCaller(ApiCaller.GET_GROUP_6_A, appDataProvider.getIp())
        apiCaller.mGroup6a = model.mGroup6A
        apiCaller.isRecursive = true
        apiCaller.delay = appDataProvider.getGraphUpdate().toLong()

        apiCaller.start()
    }



    private fun initGraph1() {

        //Graph 1
        graph1.setViewPortOffsets(0f, 0f, 0f, 0f)
        graph1.setBackgroundColor(Color.rgb(104, 241, 175))

        // no description text
        graph1.description.isEnabled = false

        // enable touch gestures
        graph1.setTouchEnabled(true)

        // enable scaling and dragging
        graph1.isDragEnabled = false
        graph1.setScaleEnabled(false)

        // if disabled, scaling can be done on x- and y-axis separately
        graph1.setPinchZoom(false)

        graph1.setDrawGridBackground(false)
        graph1.maxHighlightDistance = 300f

        val x = graph1.xAxis
        x.isEnabled = false

        val y = graph1.axisLeft
        y.typeface = tfLight
        y.setLabelCount(6, false)
        y.textColor = Color.WHITE
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        y.setDrawGridLines(false)
        y.axisLineColor = Color.WHITE
        y.axisMinimum = appDataProvider.getActPressureMin()
        y.axisMaximum = appDataProvider.getActPressureMax()

        // add data

        // lower max, as cubic runs significantly slower than linear


        graph1.legend.isEnabled = false

        graph1.animateXY(2000, 2000)

        // don't forget to refresh the drawing


        var data = LineData()
        data.setValueTextColor(Color.WHITE)

        // add empty data
        graph1.data = data

        data = graph1.data

        if (data != null) {

            for (i in 1..MAX_ENTRIES){
                var set: ILineDataSet? = data.getDataSetByIndex(0)
                // set.addEntry(...); // can be called as well

                if (set == null) {
                    set = createSet()
                    set.mode = LineDataSet.Mode.LINEAR
                    set.cubicIntensity = 0.2f
                    set.setDrawFilled(true)
                    set.setDrawCircles(false)
                    set.lineWidth = 1.8f
                    set.circleRadius = 4f
                    set.setCircleColor(Color.WHITE)
                    set.color = Color.WHITE
                    set.fillColor = Color.WHITE
                    set.fillAlpha = 0
                    set.setDrawHorizontalHighlightIndicator(false)

                    data.addDataSet(set)

                }
                data.addEntry(
                    Entry(set.entryCount.toFloat(), 0f),
                    0
                )
            }

        }
        graph1.invalidate()

    }


    private fun initGraph2() {

        //Graph 2
        graph2.setViewPortOffsets(0f, 0f, 0f, 0f)
        graph2.setBackgroundColor(Color.rgb(104, 241, 175))

        // no description text
        graph2.description.isEnabled = false

        // enable touch gestures
        graph2.setTouchEnabled(true)

        // enable scaling and dragging
        graph2.isDragEnabled = false
        graph2.setScaleEnabled(false)

        // if disabled, scaling can be done on x- and y-axis separately
        graph2.setPinchZoom(false)

        graph2.setDrawGridBackground(false)
        graph2.maxHighlightDistance = 300f

        val x = graph2.xAxis
        x.isEnabled = false

        val y = graph2.axisLeft
        y.typeface = tfLight
        y.setLabelCount(6, false)
        y.textColor = Color.WHITE
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        y.setDrawGridLines(false)
        y.axisLineColor = Color.WHITE
        y.axisMinimum = appDataProvider.getActFlowMin()
        y.axisMaximum = appDataProvider.getActFlowMax()

        // add data

        // lower max, as cubic runs significantly slower than linear


        graph2.legend.isEnabled = false

        graph2.animateXY(2000, 2000)

        // don't forget to refresh the drawing


        var data = LineData()
        data.setValueTextColor(Color.WHITE)

        // add empty data
        graph2.data = data

        data = graph2.data

        if (data != null) {

            for (i in 1..MAX_ENTRIES){
                var set: ILineDataSet? = data.getDataSetByIndex(0)
                // set.addEntry(...); // can be called as well

                if (set == null) {
                    set = createSet()
                    set.mode = LineDataSet.Mode.LINEAR
                    set.cubicIntensity = 0.2f
                    set.setDrawFilled(true)
                    set.setDrawCircles(false)
                    set.lineWidth = 1.8f
                    set.circleRadius = 4f
                    set.setCircleColor(Color.WHITE)
                    set.color = Color.WHITE
                    set.fillColor = Color.WHITE
                    set.fillAlpha = 0
                    set.setDrawHorizontalHighlightIndicator(false)

                    data.addDataSet(set)

                }
                data.addEntry(
                    Entry(set.entryCount.toFloat(), 0f),
                    0
                )
            }

        }
        graph2.invalidate()
    }

    private fun initGraph3() {

        //graph 3
        graph3.setViewPortOffsets(0f, 0f, 0f, 0f)
        graph3.setBackgroundColor(Color.rgb(104, 241, 175))

        // no description text
        graph3.description.isEnabled = false

        // enable touch gestures
        graph3.setTouchEnabled(true)

        // enable scaling and dragging
        graph3.isDragEnabled = false
        graph3.setScaleEnabled(false)

        // if disabled, scaling can be done on x- and y-axis separately
        graph3.setPinchZoom(false)

        graph3.setDrawGridBackground(false)
        graph3.maxHighlightDistance = 300f

        val x = graph3.xAxis
        x.isEnabled = false

        val y = graph3.axisLeft
        y.typeface = tfLight
        y.setLabelCount(6, false)
        y.textColor = Color.WHITE
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        y.setDrawGridLines(false)
        y.axisLineColor = Color.WHITE
        y.axisMinimum = appDataProvider.getActVolMin()
        y.axisMaximum = appDataProvider.getActVolMax()

        // add data

        // lower max, as cubic runs significantly slower than linear


        graph3.legend.isEnabled = false

        graph3.animateXY(2000, 2000)

        // don't forget to refresh the drawing


        var data = LineData()
        data.setValueTextColor(Color.WHITE)

        // add empty data
        graph3.data = data

        data = graph3.data

        if (data != null) {

            for (i in 1..MAX_ENTRIES){
                var set: ILineDataSet? = data.getDataSetByIndex(0)
                // set.addEntry(...); // can be called as well

                if (set == null) {
                    set = createSet()
                    set.mode = LineDataSet.Mode.LINEAR
                    set.cubicIntensity = 0.2f
                    set.setDrawFilled(true)
                    set.setDrawCircles(false)
                    set.lineWidth = 1.8f
                    set.circleRadius = 4f
                    set.setCircleColor(Color.WHITE)
                    set.color = Color.WHITE
                    set.fillColor = Color.WHITE
                    set.fillAlpha = 0
                    set.setDrawHorizontalHighlightIndicator(false)

                    data.addDataSet(set)

                }
                data.addEntry(
                    Entry(set.entryCount.toFloat(), 0f),
                    0
                )
            }

        }
        graph3.invalidate()
    }

    private fun addEntryForGraph1(value: Float) {

        val data = graph1.data

        if (data != null) {

            var set: ILineDataSet? = data.getDataSetByIndex(0)
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet()
                data.addDataSet(set)
            }

            data.addEntry(
                Entry(set.entryCount.toFloat(), value),
                0
            )


            Log.e(TAG,"entries: "+data.entryCount +" data set: "+data.dataSetCount)


            data.notifyDataChanged()

            // let the chart know it's data has changed
            graph1.notifyDataSetChanged()

            // limit the number of visible entries
            graph1.setVisibleXRangeMaximum(MAXX)
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            graph1.moveViewToX(data.entryCount.toFloat())

            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);


        }
    }

    private fun addEntryForGraph2(value: Float) {

        val data = graph2.data

        if (data != null) {

            var set: ILineDataSet? = data.getDataSetByIndex(0)
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet()
                data.addDataSet(set)
            }

            data.addEntry(
                Entry(set.entryCount.toFloat(), value),
                0
            )

            Log.e(TAG,"entries: "+data.entryCount +" data set: "+data.dataSetCount)


            data.notifyDataChanged()

            // let the chart know it's data has changed
            graph2.notifyDataSetChanged()

            // limit the number of visible entries
            graph2.setVisibleXRangeMaximum(MAXX)
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            graph2.moveViewToX(data.entryCount.toFloat())

            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private fun addEntryForGraph3(value: Float) {

        val data = graph3.data

        if (data != null) {

            var set: ILineDataSet? = data.getDataSetByIndex(0)
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet()
                data.addDataSet(set)
            }

            data.addEntry(
                Entry(set.entryCount.toFloat(), value),
                0
            )

            Log.e(TAG,"entries: "+data.entryCount +" data set: "+data.dataSetCount)


            data.notifyDataChanged()

            // let the chart know it's data has changed
            graph3.notifyDataSetChanged()

            // limit the number of visible entries
            graph3.setVisibleXRangeMaximum(MAXX)
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            graph3.moveViewToX(data.entryCount.toFloat())

            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private fun createSet(): LineDataSet {

        val set = LineDataSet(null, "Dynamic Data")
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.color = ColorTemplate.getHoloBlue()
        set.setCircleColor(Color.WHITE)
        set.lineWidth = 2f
        set.circleRadius = 4f
        set.fillAlpha = 65
        set.fillColor = ColorTemplate.getHoloBlue()
        set.highLightColor = Color.rgb(244, 117, 117)
        set.valueTextColor = Color.WHITE
        set.valueTextSize = 9f
        set.setDrawValues(false)
        return set
    }

    override fun onDestroy() {
        apiCaller.stopExecution()
        super.onDestroy()
    }

}
