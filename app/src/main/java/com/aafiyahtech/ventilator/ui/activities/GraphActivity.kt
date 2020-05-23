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
        private const val MAX_ENTRIES = 10
        private const val MAXX = 10f

    }

    private var showUpdates = true
    private lateinit var apiCaller: ApiCaller

    private  lateinit var tfRegular: Typeface
    private lateinit var tfLight: Typeface
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

        initGraph1()
        initGraph2()
        initGraph3()


        apiCaller = ApiCaller(ApiCaller.GET_GROUP_6_A, AppDataProvider.getInstance(this.applicationContext).getIp())
        apiCaller.mGroup6a = model.mGroup6A
        apiCaller.isRecursive = true

        apiCaller.start()
    }



    private fun initGraph1() {

        //Graph 1
        graph1.setOnChartValueSelectedListener(this)

        // enable description text
        graph1.description.isEnabled = false

        // enable touch gestures
        graph1.setTouchEnabled(false)

        // enable scaling and dragging
        graph1.isDragEnabled = false
        graph1.setScaleEnabled(false)
        graph1.setDrawGridBackground(false)

        // if disabled, scaling can be done on x- and y-axis separately
        graph1.setPinchZoom(false)

        // set an alternative background color
        graph1.setBackgroundColor(Color.LTGRAY)

        var data = LineData()
        data.setValueTextColor(Color.WHITE)

        // add empty data
        graph1.data = data
        // get the legend (only possible after setting data)
        val l = graph1.legend

        // modify the legend ...
        l.form = Legend.LegendForm.LINE
        l.typeface = tfLight
        l.textColor = Color.WHITE

        val xl = graph1.xAxis
        xl.typeface = tfLight
        xl.textColor = Color.WHITE
        xl.setDrawGridLines(false)
        xl.setAvoidFirstLastClipping(true)
        xl.isEnabled = true

        val leftAxis = graph1.axisLeft
        leftAxis.typeface = tfLight
        leftAxis.textColor = Color.WHITE
        leftAxis.axisMaximum = 30f
        leftAxis.axisMinimum = 0f
        leftAxis.setDrawGridLines(true)

        val rightAxis = graph1.axisRight
        rightAxis.isEnabled = false

        data = graph1.data

        if (data != null) {

            for (i in 1..MAX_ENTRIES){
                var set: ILineDataSet? = data.getDataSetByIndex(0)
                // set.addEntry(...); // can be called as well

                if (set == null) {
                    set = createSet()
                    data.addDataSet(set)
                }

                data.addEntry(
                    Entry(set.entryCount.toFloat(), 0f),
                    0
                )
            }
        }
    }

    private fun initGraph2() {

        //Graph 1
        graph2.setOnChartValueSelectedListener(this)

        // enable description text
        graph2.description.isEnabled = false

        // enable touch gestures
        graph2.setTouchEnabled(false)

        // enable scaling and dragging
        graph2.isDragEnabled = false
        graph2.setScaleEnabled(false)
        graph2.setDrawGridBackground(false)

        // if disabled, scaling can be done on x- and y-axis separately
        graph2.setPinchZoom(false)

        // set an alternative background color
        graph2.setBackgroundColor(Color.LTGRAY)

        var data = LineData()
        data.setValueTextColor(Color.WHITE)

        // add empty data
        graph2.data = data
        // get the legend (only possible after setting data)
        val l = graph2.legend

        // modify the legend ...
        l.form = Legend.LegendForm.LINE
        l.typeface = tfLight
        l.textColor = Color.WHITE

        val xl = graph2.xAxis
        xl.typeface = tfLight
        xl.textColor = Color.WHITE
        xl.setDrawGridLines(false)
        xl.setAvoidFirstLastClipping(true)
        xl.isEnabled = true

        val leftAxis = graph2.axisLeft
        leftAxis.typeface = tfLight
        leftAxis.textColor = Color.WHITE
        leftAxis.axisMaximum = 30f
        leftAxis.axisMinimum = 0f
        leftAxis.setDrawGridLines(true)

        val rightAxis = graph2.axisRight
        rightAxis.isEnabled = false

        data = graph2.data

        if (data != null) {

            for (i in 1..MAX_ENTRIES){
                var set: ILineDataSet? = data.getDataSetByIndex(0)
                // set.addEntry(...); // can be called as well

                if (set == null) {
                    set = createSet()
                    data.addDataSet(set)
                }

                data.addEntry(
                    Entry(set.entryCount.toFloat(), 0f),
                    0
                )
            }
        }
    }

    private fun initGraph3() {

        //Graph 1
        graph3.setOnChartValueSelectedListener(this)

        // enable description text
        graph3.description.isEnabled = false

        // enable touch gestures
        graph3.setTouchEnabled(false)

        // enable scaling and dragging
        graph3.isDragEnabled = false
        graph3.setScaleEnabled(false)
        graph3.setDrawGridBackground(false)

        // if disabled, scaling can be done on x- and y-axis separately
        graph3.setPinchZoom(false)

        // set an alternative background color
        graph3.setBackgroundColor(Color.LTGRAY)

        var data = LineData()
        data.setValueTextColor(Color.WHITE)

        // add empty data
        graph3.data = data
        // get the legend (only possible after setting data)
        val l = graph3.legend

        // modify the legend ...
        l.form = Legend.LegendForm.LINE
        l.typeface = tfLight
        l.textColor = Color.WHITE

        val xl = graph3.xAxis
        xl.typeface = tfLight
        xl.textColor = Color.WHITE
        xl.setDrawGridLines(false)
        xl.setAvoidFirstLastClipping(true)
        xl.isEnabled = true

        val leftAxis = graph3.axisLeft
        leftAxis.typeface = tfLight
        leftAxis.textColor = Color.WHITE
        leftAxis.axisMaximum = 30f
        leftAxis.axisMinimum = 0f
        leftAxis.setDrawGridLines(true)

        val rightAxis = graph3.axisRight
        rightAxis.isEnabled = false

        data = graph3.data

        if (data != null) {

            for (i in 1..MAX_ENTRIES){
                var set: ILineDataSet? = data.getDataSetByIndex(0)
                // set.addEntry(...); // can be called as well

                if (set == null) {
                    set = createSet()
                    data.addDataSet(set)
                }

                data.addEntry(
                    Entry(set.entryCount.toFloat(), 0f),
                    0
                )
            }
        }
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
