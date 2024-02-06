package space.grosu.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Level1 extends AppCompatActivity {

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        //создаем переменную text_levels
        TextView text_levels = findViewById(R.id.text_levels);
        text_levels.setText(R.string.level1); //установили текст


        final ImageView img_left = (ImageView) findViewById(R.id.img_left);
        //Код который скругляет углы левой картинки
        img_left.setClipToOutline(true);

        final ImageView img_right = (ImageView) findViewById(R.id.img_right);
        //Код который скругляет углы правой картинки
        img_right.setClipToOutline(true);

        //Развернуть игру на всеь экран - начало
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //Развернуть игру на всеь экран - конец

        //Вызов диалогового окна в начале игры
        dialog = new Dialog(this); //создаем новое диалоговое окно
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //скрываем заголовок
        dialog.setContentView(R.layout.previewdialog); //путь к макету диалогового окна
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //прозрачный фон диалогового окна
        dialog.setCancelable(false); //окно нельзя закрыть кнопкой назад

        //кнопка которая закрывает диалоговое окно - начало
        TextView btnclose = (TextView) dialog.findViewById(R.id.btnclose);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //обрабатываем нажатие кнопки - начало
                try {
                    //вернуться назад к выбору уровня - начало
                    Intent intent = new Intent(Level1.this, GameLevels.class); //Создал намерение для перехода
                    startActivity(intent); //старт намерения
                    finish(); //закрыть этот класс
                    //вернуться назад к выбору уровня - конец


                } catch (Exception e) {
                    //Здесь кода не будет
                }
                dialog.dismiss(); //закрываем диалоговое окно
                //обрабатываем нажатие кнопки - конец

            }
        });
        //кнопка которая закрывает диалоговое окно - конец

        //кнопка "продолжить" -начало
        Button btncontinue = (Button) dialog.findViewById(R.id.btncontinue);
        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss(); //диалоговое окно закрывается
            }
        });
        //кнопка "продолжить" -конец


        dialog.show(); //показать диалоговое окно

        //Кнопка "назад" - начало
        Button btn_back = (Button) findViewById(R.id.button_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Обрабатываем нажатие кнопки назад - начало
                try {
                    //вернуться назад к выбору уровня - начало
                    Intent intent = new Intent(Level1.this, GameLevels.class); //создал намерение для перехода
                    startActivity(intent); //старт намеревания
                    finish(); //закрыть этот класс
                    //вернуться назад к выбору уровня - конец

                } catch (Exception e) {
                    //здесь кода не будет
                }
                //Обрабатываем нажатие кнопки назад - конец

            }
        });
        //Кнопка "назад" - конец

    }

    //системная кнопка назад - начло
    @Override
    public void onBackPressed(){
//Обрабатываем нажатие кнопки назад - начало
        try {
            //вернуться назад к выбору уровня - начало
            Intent intent = new Intent(Level1.this, GameLevels.class); //создал намерение для перехода
            startActivity(intent); //старт намеревания
            finish(); //закрыть этот класс
            //вернуться назад к выбору уровня - конец

        } catch (Exception e) {
            //здесь кода не будет
        }
        //Обрабатываем нажатие кнопки назад - конец
    }
    //системная кнопка назад - конец

}