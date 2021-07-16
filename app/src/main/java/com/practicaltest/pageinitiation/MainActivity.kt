package com.practicaltest.pageinitiation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicaltest.pageinitiation.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var apiInterface: ApiInterface
    lateinit var dataAdapter: DataAdapter
    var itemList = ArrayList<DemoData?>()
    lateinit var loadMoreItemsCells: ArrayList<DemoData?>
    lateinit var scrollListener: RecyclerViewLoadMoreScroll
    private lateinit var linearLayoutManager: LinearLayoutManager
    var loadMore = false
    val TAG = "MainActivity"
    private var Limit = "10"
    private var Offset = 0
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRVLayoutManager()
        setAdapter()
        setRVScrollListener()

        loadMore = false
        setAPICall()
    }
    private fun setRVLayoutManager() {
        linearLayoutManager = LinearLayoutManager(this)
        binding.recycleView.layoutManager = linearLayoutManager
        binding.recycleView.setHasFixedSize(true)
    }
    fun setAdapter(){
        apiInterface = RetrofitClent.client.create(ApiInterface::class.java)
        itemList = ArrayList()
        dataAdapter = DataAdapter(itemList)
        dataAdapter.notifyDataSetChanged()
        binding.recycleView.adapter = dataAdapter
    }
    private fun setRVScrollListener() {
        linearLayoutManager = LinearLayoutManager(this)
        scrollListener = RecyclerViewLoadMoreScroll(linearLayoutManager)
        binding.recycleView.setHasFixedSize(true)
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                Log.d(TAG,""+scrollListener.getLoaded())
                loadMoreData()
            }
        })
        binding.recycleView.addOnScrollListener(scrollListener)
    }
    private fun loadMoreData() {
        loadMore = true
        dataAdapter.addLoadingView()
        loadMoreItemsCells = ArrayList()
        Offset += 10
        Handler().postDelayed({
           setAPICall()
        }, 3000)

    }
    fun setAPICall() {
        val call = apiInterface.fetchData(Limit,Offset.toString())
        call.enqueue(object : Callback<ArrayList<DemoData>> {
            override fun onResponse(call: Call<ArrayList<DemoData>>, response: Response<ArrayList<DemoData>>) {
                val datalist1: ArrayList<DemoData>? = response.body()
                val datalist: ArrayList<DemoData?> = ArrayList()
                datalist1?.let { datalist.addAll(it) }
                progress_bar.visibility = View.GONE
                if(loadMore){
                    Log.d(TAG,"size1: "+loadMoreItemsCells.size+" loadMore: "+loadMore+" Offset: "+Offset)
                    if(datalist.isNotEmpty()) {
                        loadMoreItemsCells.addAll(datalist)
                        dataAdapter.removeLoadingView()
                        dataAdapter.addData(loadMoreItemsCells)
                        scrollListener.setLoaded()
                    }else{
                        loadMore = false
                        Offset = 10
                        scrollListener.setLoaded()
                        Toast.makeText(applicationContext, "No more data", Toast.LENGTH_SHORT).show()
                        setAPICall()
                    }
                }else {
                    dataAdapter.setItems(datalist)
                }
                binding.recycleView.post {
                    dataAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ArrayList<DemoData>>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Log.e(TAG, "Got error : " + t.localizedMessage)
                Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT).show()

            }
        })

    }
}