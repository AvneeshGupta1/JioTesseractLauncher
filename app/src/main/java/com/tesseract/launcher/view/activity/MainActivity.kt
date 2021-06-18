package com.tesseract.launcher.view.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.animation.LayoutAnimationController
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.tesseract.launcher.R
import com.tesseract.launcher.databinding.ActivityHomeBinding
import com.tesseract.launcher.utils.hide
import com.tesseract.launcherlibrary.AppBackListElement
import com.tesseract.launcher.utils.isMyAppLauncherDefault
import com.tesseract.launcher.utils.setDivider
import com.tesseract.launcher.view.adapters.ApplicationListAdapter
import com.tesseract.launcher.view.view.ItemOffsetDecoration
import com.tesseract.launcher.view.view.LauncherTextWatcher
import com.tesseract.launcher.viewmodel.HomeViewModel
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val appList = ArrayList<AppBackListElement>()
    private lateinit var appListAdapter: ApplicationListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mRecyclerView
        loadAppList()
        binding.edtSearch.addTextChangedListener(object : LauncherTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                appListAdapter.filter.filter(s)
            }
        })
        if (!isMyAppLauncherDefault(this)) {
            launchAppChooser()
        }
        addInstallUninstallReceiver()
    }


    private fun loadAppList() {
        val startupIntent = Intent(Intent.ACTION_MAIN, null)
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val backupViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        backupViewModel.getAppBackList().observe(this, {
            binding.progressLayout.hide()
            if (it != null) {
                appList.clear()
                appList.addAll(it)
                appListAdapter.notifyDataSetChanged()
                setAnimation()
            }
        })
    }

    override fun onBackPressed() {

    }

    private val mRecyclerView by lazy {
        binding.appRecyclerView.itemAnimator = DefaultItemAnimator()
        setAnimation()
        appListAdapter = ApplicationListAdapter(appList,
            { item -> launchApplication(item) },
            { item -> onDeleteClick(item) })
        binding.appRecyclerView.adapter = appListAdapter
    }

    private fun onDeleteClick(item: AppBackListElement) {
        alert(R.string.uninstallConfirmMsg, R.string.uninstallTitle) {
            positiveButton(R.string.label_uninstall) {
                try {
                    val packageURI = Uri.parse("package:" + item.packageName)
                    val uninstallIntent = Intent(Intent.ACTION_DELETE, packageURI)
                    startActivity(uninstallIntent)
                } catch (e: Exception) {
                }
            }
            negativeButton(R.string.alert_cancel)
        }.show()
    }

    private fun setAnimation() {
        val itemDecorator = ItemOffsetDecoration(this, R.dimen.item_offset)
        val controller = LayoutAnimationController(this, null)
        controller.delay = .08f
        controller.order = LayoutAnimationController.ORDER_NORMAL
        controller.setAnimation(this, R.anim.slide_up)
        binding.appRecyclerView.layoutAnimation = controller
        binding.appRecyclerView.setDivider()
        binding.appRecyclerView.addItemDecoration(itemDecorator)

    }

    private fun launchApplication(item: AppBackListElement) {
        item.packageName?.let {
            val launchIntent = packageManager.getLaunchIntentForPackage(it)
            startActivity(launchIntent)
        }
    }

    private fun launchAppChooser() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }


    override fun onDestroy() {
        unregisterReceiver(installUninstallReceiver)
        super.onDestroy()
    }

    private fun addInstallUninstallReceiver() {
        val installFilter = IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
        }
        installFilter.addDataScheme("package")
        registerReceiver(installUninstallReceiver, installFilter)
    }

    private val installUninstallReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.action?.let {
                if (it == Intent.ACTION_PACKAGE_ADDED) {
                    toast(R.string.application_install)
                }
                if (it == Intent.ACTION_PACKAGE_REMOVED) {
                    toast(R.string.application_uninstall)
                }
            }
            loadAppList()
        }
    }
}