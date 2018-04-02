# 对话框效果弹窗

- 效果图
<img src="https://github.com/mnnyang/android_auto_location_popup_window/blob/master/screenfetch.gif" width="260" height="auto">

1.  

    ```java
    allprojects {
        repositories {
            maven { url "https://jitpack.io" }
        }
    }
    ```
    
2. 
    ```java
    dependencies {
        compile 'com.github.mnnyang:android_auto_location_popup_window:0.2.0'
    }
    ```
    
3. 
    ```java
    mMenuWindow = MenuWindow.with(this)
                    .isBubble(true)
                    .anim(R.style.window_anim)
                    .align(AutoLocationWindow.ALIGN_RIGHT)
                    .addMenuItem(new MenuItem()
                            .setTitle("创建群聊").setIconRes(R.drawable.conversation_options_multichat))
                    .addMenuItem(new MenuItem()
                            .setTitle("加好友/群").setIconRes(R.drawable.conversation_options_addmember))
                    .addMenuItem(new MenuItem()
                            .setTitle("扫一扫").setIconRes(R.drawable.conversation_options_qr))
                    .addMenuItem(new MenuItem()
                            .setTitle("面对面快传").setIconRes(R.drawable.conversation_facetoface_qr))
                    .addMenuItem(new MenuItem()
                            .setTitle("付款").setIconRes(R.drawable.conversation_options_charge_icon))
                    .addMenuItem(new MenuItem()
                            .setTitle("拍摄").setIconRes(R.drawable.conversation_options_camera))
                    .addMenuItem(new MenuItem()
                            .setTitle("高能舞室").setIconRes(R.drawable.conversation_options_multichat))
                    .setMenuClickListener(new MenuWindow.MenuClickListener() {
                        @Override
                        public boolean onClick(MenuItem item) {
                            Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT)
                                    .show();
                            return true;
                        }
                    }).show(mIvMenu);
    ```


