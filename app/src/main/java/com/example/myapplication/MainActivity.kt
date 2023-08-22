package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.HomeFragment
import com.example.myapplication.R
import com.example.myapplication.TalkFragment
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    private val HomeFragment = HomeFragment()
    private val TalkFragment = TalkFragment()

    private val Fragment_1 = 1
    private val Fragment_2 = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //버튼
        findViewById<View>(R.id.lButton).setOnClickListener {
            fragmentView(Fragment_1)
        }

        findViewById<View>(R.id.mButton).setOnClickListener {
            fragmentView(Fragment_2)
        }
        fragmentView(Fragment_1)



        // 하단 탭이 눌렸을 때 화면을 전환하기 위해선 이벤트 처리하기 위해 BottomNavigationView 객체 생성
        var bnv_main = findViewById(R.id.bottomNavi) as BottomNavigationView

        // OnNavigationItemSelectedListener를 통해 탭 아이템 선택 시 이벤트를 처리
        // navi_menu.xml 에서 설정했던 각 아이템들의 id를 통해 알맞은 프래그먼트로 변경하게 한다.
        bnv_main.run { setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav1 -> {
                    // 다른 프래그먼트 화면으로 이동하는 기능
                    val homeFragment = HomeFragment()
                    switchFragment(homeFragment)
                }
                R.id.nav2 -> {
                    val talkFragment = TalkFragment()
                    switchFragment(talkFragment)
                }
//                R.id.nav3 -> {
//                    val settingFragment = SettingFragment()
//                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, settingFragment).commit()
//                }
            }
            true
        }
            selectedItemId = R.id.nav1
        }
    }

    //버튼
    private fun fragmentView(fragment: Int) {
        // FragmentTransaction를 이용해 프래그먼트를 사용합니다.
        val transaction = supportFragmentManager.beginTransaction()

        when(fragment) {
            1 -> {
                val fragment1 = HomeFragment()
                transaction.replace(R.id.main_layout, fragment1)
                transaction.commit()
            }
            2 -> {
                // 두번 째 프래그먼트 호출
                val fragment2 = ListFragment()
                transaction.replace(R.id.main_layout, fragment2)
                transaction.commit()
            }
        }
    }


            private fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_layout, fragment)
        transaction.addToBackStack(null) // Optional: Add the fragment to the back stack
        transaction.commit()
    }

    override fun onStart() {
        super.onStart()


    }

//    private fun switchFragment(fragment: Fragment) {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.main_layout, fragment)
//        transaction.addToBackStack(null) // Optional: Add the fragment to the back stack
//        transaction.commit()
//    }
}
