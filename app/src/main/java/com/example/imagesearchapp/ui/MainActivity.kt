package com.example.imagesearchapp.ui
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.imagesearchapp.core.CoreEvent
import com.example.imagesearchapp.core.CoreLib
import com.example.imagesearchapp.core.Event
import com.example.imagesearchapp.core.EventListner
import com.example.imagesearchapp.Constants
import com.example.imagesearchapp.R
import com.example.imagesearchapp.databinding.ActivityMainBinding
import com.example.imagesearchapp.ui.ImageListViewModel
import com.example.imagesearchapp.model.ImageData
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() , EventListner {
    lateinit var binding : ActivityMainBinding
    val viewModel: ImageListViewModel by viewModels()
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        navController = navHostFragment!!.findNavController()
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return  navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        CoreLib.getDispatcher()?.addEventListener(Constants.ADAPTER_CLICK,this)
        CoreLib.getDispatcher()?.addEventListener(Constants.PROGRESS_STOP,this)
        CoreLib.getDispatcher()?.addEventListener(Constants.PROGRESS_START,this)
    }

    override fun onPause() {
        super.onPause()
        CoreLib.getDispatcher()?.removeEventListener(Constants.ADAPTER_CLICK,this)
        CoreLib.getDispatcher()?.removeEventListener(Constants.PROGRESS_STOP,this)
        CoreLib.getDispatcher()?.removeEventListener(Constants.PROGRESS_START,this)
    }

    override fun onEvent(event: Event?) {
        if (event?.type == Constants.ADAPTER_CLICK){
            val coreEvent = event as CoreEvent
            val imageData = coreEvent.getmExtra()?.get(Constants.ARGUMENTS) as ImageData
            viewModel.imageData.value = imageData
            navController.navigate(R.id.imageDetailFragment)
        }else if(event?.type == Constants.PROGRESS_STOP){
            binding.progressBar.visibility= View.GONE
        } else if(event?.type == Constants.PROGRESS_START){
            binding.progressBar.visibility= View.VISIBLE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
           initalizeSearchView(menu)
        return super.onCreateOptionsMenu(menu)
    }


    fun initalizeSearchView( menu: Menu?){
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                querytDispatcher(searchView,searchItem,query!!)
                return  true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return  false
            }

        })
    }

    fun querytDispatcher( searchView: SearchView,searchItem:MenuItem,query:String){
        searchView.clearFocus()
        searchView.setQuery("",false)
        searchItem.collapseActionView()
        binding.progressBar.visibility = View.VISIBLE
        val bundle = Bundle()
        bundle.putString(Constants.ARGUMENTS,query)
        CoreLib.getDispatcher()?.dispatchEvent(CoreEvent(Constants.SEARCH_QUERY,bundle))

    }



}
