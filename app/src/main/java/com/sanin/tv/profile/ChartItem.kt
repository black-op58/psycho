package com.sanin.tv.profile
import android.content.Intent
import android.view.View
import com.sanin.tv.R
import com.sanin.tv.databinding.ItemChartBinding
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AAMoveOverEventMessageModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAOptions
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.OnItemLongClickListener
import com.xwray.groupie.viewbinding.BindableItem
import com.xwray.groupie.viewbinding.GroupieViewHolder
class ChartItem(    
private val title: String,    
private val aaOptions: AAOptions,    
private val activity: ProfileActivity) : BindableItem<ItemChartBinding>() {
    private lateinit var binding: ItemChartBinding    
override fun bind(viewBinding: ItemChartBinding, position: Int) {        
        b
val callback: AAChartView.AAChartViewCallBack = 
object : AAChartView.AAChartViewCallBack {
    override fun chartViewDidFinishLoad(aaChartView: AAChartView) {                
        b

override fun chartViewMoveOverEventMessage(                aaChartView: AAChartView,                messageModel: AAMoveOverEventMessageModel            ) {            
        }
binding.chartView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)        binding.chartView.callBack = callback
        binding.chartView.reload()
        binding.chartView.aa_drawChartWithChartOptions(aaOptions)
        binding.openButton.setOnClickListener {
            SingleStatActivity.chartOptions = aaOptions            activity.startActivity(                Intent(activity, SingleStatActivity::class.java)            )
}
}

override fun getLayout(): Int {
return R.layout.item_chart    }

override fun initializeViewBinding(view: View): ItemChartBinding {
return ItemChartBinding.bind(view)
     }
override fun bind(viewHolder: GroupieViewHolder<ItemChartBinding>, position: Int) {        
        v
    }

override fun bind(        viewHolder: GroupieViewHolder<ItemChartBinding>,        position: Int,        payloads: MutableList<Any>    ) {        
        v
    }

override fun bind(        viewHolder: GroupieViewHolder<ItemChartBinding>,        position: Int,        payloads: MutableList<Any>,        onItemClickListener: OnItemClickListener?,        onItemLongClickListener: OnItemLongClickListener?    ) {        
        v
    }

override fun getViewType(): Int {
return 0    }}