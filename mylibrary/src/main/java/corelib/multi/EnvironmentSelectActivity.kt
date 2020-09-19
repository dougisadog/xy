package corelib.multi

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import corelib.extension.append

open class EnvironmentSelectActivity : Activity() {

    private var environmentItems: MutableList<EnvironmentItem> = mutableListOf()
    private var settingSpinners: MutableList<Spinner> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addData()

        val contentlayout = LinearLayout(this)
        contentlayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        contentlayout.orientation = LinearLayout.VERTICAL

        val headerLabel = TextView(this)
        headerLabel.layoutParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 140)
        headerLabel.gravity = Gravity.CENTER
        headerLabel.setBackgroundColor(Color.BLACK)
        headerLabel.setTextColor(Color.WHITE)
        headerLabel.text = "Please select server"
        contentlayout.addView(headerLabel)

        val scrollLayout = LinearLayout(this)
        scrollLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        scrollLayout.orientation = LinearLayout.VERTICAL

        for (item in environmentItems) {
            val layout = LinearLayout(this)
            layout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )

            val textView = TextView(this)
            textView.layoutParams =
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 100)
            textView.text = item.settingLabel
            layout.addView(textView)

            val spinner = Spinner(this)
            spinner.layoutParams =
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 100)
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, item.settingItemKeys.toTypedArray()
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            layout.addView(spinner)

            settingSpinners.add(spinner)

            scrollLayout.addView(layout)
        }

        val scrollView = ScrollView(this)
        scrollView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        scrollView.addView(scrollLayout)

        contentlayout.addView(scrollView)

        val okButton = Button(this)
        okButton.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        okButton.text = "OK"
        contentlayout.addView(okButton)

        setContentView(contentlayout)

        okButton.setOnClickListener {
            onEnvironmentResultLoaded(getEnvironmentResult())
        }
    }

    fun addItem(label: String, name: String, items: LinkedHashMap<String, String>) {
        environmentItems.append(EnvironmentItem(label, name, items))
    }

    open fun addData() {}

    private fun getEnvironmentResult(): LinkedHashMap<String, String> {
        var selectedItems: LinkedHashMap<String, String> = linkedMapOf()
        environmentItems.forEachIndexed { index, item ->
            val selectKey = settingSpinners[index].selectedItem
            val selectValue = item.settingItems[selectKey]
            selectedItems[item.settingName] = selectValue!!
        }
        return selectedItems
    }

    /**
     *  Environments selected callback
     */
    open fun onEnvironmentResultLoaded(items: LinkedHashMap<String, String>){
    }

}