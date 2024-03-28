package space.grosu.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Level1 extends AppCompatActivity {

    private Dialog dialog;
    private Dialog dialogEnd;
    private boolean isCorrect;
    private ArrayRome arrayRome = new ArrayRome();
    private int count;
    private int correctImageIndex;
    private int wrongImageIndex ;
    private int wrongTextIndex;
    private Random random = new Random();

    private ImageView img_left, img_right;
    private TextView text_left, text_right;
    private Animation a;

    // Массив для прогресса игры
    private final int[] progress = {
            R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5,
            R.id.point6, R.id.point7, R.id.point8, R.id.point9, R.id.point10,
            R.id.point11, R.id.point12, R.id.point13, R.id.point14, R.id.point15,
            R.id.point16, R.id.point17, R.id.point18, R.id.point19, R.id.point20,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        // Настройка TextView levels
        TextView text_levels = findViewById(R.id.text_levels);
        text_levels.setText(R.string.level1);

        // Инициализация ImageView
        img_left = findViewById(R.id.img_left);
        img_right = findViewById(R.id.img_right);

        // Скругление углов картинок
        img_left.setClipToOutline(true);
        img_right.setClipToOutline(true);

        // Текстовые поля
        text_left = findViewById(R.id.text_left);
        text_right = findViewById(R.id.text_right);

        // Развернуть игру на весь экран
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Диалоговое окно в начале игры
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        // Кнопка закрытия диалогового окна
        TextView btnclose = dialog.findViewById(R.id.btnclose);
        btnclose.setOnClickListener(v -> {
            try {
                // Вернуться к выбору уровня
                Intent intent = new Intent(Level1.this, GameLevels.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                //
            }
            dialog.dismiss();
        });

        // Кнопка "Продолжить"
        Button btncontinue = dialog.findViewById(R.id.btncontinue);
        btncontinue.setOnClickListener(v -> dialog.dismiss());

        dialog.show();

        // Диалоговое окно в конце игры
        dialogEnd = new Dialog(this);
        dialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEnd.setContentView(R.layout.dialogend);
        dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogEnd.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        dialogEnd.setCancelable(false);


        // Кнопка закрытия диалогового окна
        TextView btnclose2 = dialogEnd.findViewById(R.id.btnclose);
        btnclose2.setOnClickListener(v -> {
            try {
                // Вернуться к выбору уровня
                Intent intent = new Intent(Level1.this, GameLevels.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                //
            }
            dialogEnd.dismiss();
        });

        // Кнопка "Продолжить"
        Button btncontinue2 = dialogEnd.findViewById(R.id.btncontinue);
        btncontinue2.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Level1.this, Level2.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                //
            }
            dialogEnd.dismiss();
        });

        // Кнопка "Назад"
        Button btn_back = findViewById(R.id.button_back);
        btn_back.setOnClickListener(v -> {
            try {
                // Вернуться к выбору уровня
                Intent intent = new Intent(Level1.this, GameLevels.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                //
            }
        });

        // Анимация
        a = AnimationUtils.loadAnimation(Level1.this, R.anim.alpha);

        // Загрузка первого изображения
        correctImageIndex = random.nextInt(10);
        isCorrect = (correctImageIndex < 5);

        if (isCorrect) {
            img_left.setImageResource(arrayRome.imagesRome[correctImageIndex]);
            text_left.setText(arrayRome.textsRome[correctImageIndex]);
        } else {
            img_left.setImageResource(arrayRome.imagesWrong[wrongImageIndex - 5]);
            text_left.setText(arrayRome.textsWrong[wrongTextIndex]);
        }

        wrongImageIndex  = random.nextInt(10);

        while (correctImageIndex == wrongImageIndex ) {
            wrongImageIndex  = random.nextInt(10);
        }

        img_right.setImageResource(arrayRome.imagesRome[correctImageIndex ]);
        text_right.setText(arrayRome.textsRome[correctImageIndex ]);

        img_right.setEnabled(true);

        // Обработка нажатия на левую картинку
        img_left.setOnTouchListener((v, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                img_right.setEnabled(false);

                if (isCorrect) {
                    count++;
                    img_left.setImageResource(R.drawable.yes);
                    handleCorrectAnswer();
                } else {
                    img_left.setImageResource(R.drawable.nono);
                    handleWrongAnswer();
                }
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                loadNextImages();
            }
            return true;
        });

        // Обработка нажатия на правую картинку
        img_right.setOnTouchListener((v, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                img_left.setEnabled(false);

                if (!isCorrect) {
                    count++;
                    img_right.setImageResource(R.drawable.yes);
                    handleCorrectAnswer();
                } else {
                    img_right.setImageResource(R.drawable.nono);
                    handleWrongAnswer();
                }
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                loadNextImages();
            }
            return true;
        });
    }

    // Обработка нажатия на изображение
    private void handleImageClick(ImageView imageView, boolean isLeft) {
        img_right.setEnabled(false);

        if ((isCorrect && isLeft) || (!isCorrect && !isLeft)) {
            imageView.setImageResource(R.drawable.yes);
            handleCorrectAnswer();
        } else {
            imageView.setImageResource(R.drawable.nono);
            handleWrongAnswer();
        }

        loadNextImages();
    }

    // Метод для загрузки следующих изображений
    private void loadNextImages() {
        do {
            correctImageIndex = random.nextInt(10);
            isCorrect = (correctImageIndex < 5);
            wrongImageIndex = random.nextInt(10);
        } while (correctImageIndex == wrongImageIndex);  // Ensure different images

        if (isCorrect) {
            img_left.setImageResource(arrayRome.imagesRome[correctImageIndex]);
            text_left.setText(arrayRome.textsRome[correctImageIndex]);
        } else {
            img_left.setImageResource(arrayRome.imagesWrong[wrongImageIndex - 5]);
            text_left.setText(arrayRome.textsWrong[wrongImageIndex]);
        }

        img_left.startAnimation(a);

        img_right.setImageResource(arrayRome.imagesRome[wrongImageIndex]);
        img_right.startAnimation(a);
        text_right.setText(arrayRome.textsWrong[wrongImageIndex]);  // Set text from wrong text array

        img_left.setEnabled(true);
        img_right.setEnabled(true);

        // Если 20 ответов даны
        if (count == 10) {
            // ВЫХОД ИЗ УРОВНЯ
            dialogEnd.show();
        }
    }

    private void handleCorrectAnswer() {
        if (count < 10) {
            count = count + 1;
        }

        // Закрашиваем прогресс серым цветом
        for (int i = 0; i < 10; i++) {
            TextView tv = findViewById(progress[i]);
            tv.setBackgroundResource(R.drawable.style_points);
        }

        // Определяем правильные ответы и закрашиваем зеленым
        for (int i = 0; i < count; i++) {
            TextView tv = findViewById(progress[i]);
            tv.setBackgroundResource(R.drawable.style_points_green);
        }
    }

    private void handleWrongAnswer() {
        if (count > 0) {
            if (count == 1) {
                count = 0;
            } else {
                count = count - 2;
            }
        }

        // Закрашиваем прогресс серым цветом
        for (int i = 0; i < 9; i++) {
            TextView tv = findViewById(progress[i]);
            tv.setBackgroundResource(R.drawable.style_points);
        }

        // Определяем правильные ответы и закрашиваем зеленым
        for (int i = 0; i < count; i++) {
            TextView tv = findViewById(progress[i]);
            tv.setBackgroundResource(R.drawable.style_points_green);
        }
    }

    // Метод для обновления левого изображения
    private void updateLeftImage() {
        correctImageIndex = random.nextInt(10);
        isCorrect = (correctImageIndex < 5);

        if (isCorrect) {
            img_left.setImageResource(arrayRome.imagesRome[correctImageIndex]);
            text_left.setText(arrayRome.textsRome[correctImageIndex]);
        } else {
            img_left.setImageResource(arrayRome.imagesWrong[wrongImageIndex  - 5]);
            text_left.setText(arrayRome.textsWrong[wrongImageIndex ]);
        }

        img_left.startAnimation(a);
        img_left.setEnabled(true);
    }

    // Метод для обновления правого изображения
    private void updateRightImage() {
        wrongImageIndex  = random.nextInt(10);

        while (correctImageIndex == wrongImageIndex ) {
            wrongImageIndex  = random.nextInt(10);
        }

        img_right.setImageResource(arrayRome.imagesRome[wrongImageIndex ]);
        img_right.startAnimation(a);
        text_right.setText(arrayRome.textsRome[wrongImageIndex ]);

        img_left.setEnabled(true);
    }

    // Обработка системной кнопки "Назад"
    @Override
    public void onBackPressed() {
        try {
            // Вернуться к выбору уровня
            Intent intent = new Intent(Level1.this, GameLevels.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            //
        }
    }
}
