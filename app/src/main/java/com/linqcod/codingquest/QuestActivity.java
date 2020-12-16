package com.linqcod.codingquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestActivity extends AppCompatActivity {

    private ArrayList<Message> messages = new ArrayList<>();
    private ArrayList<Question> questions = new ArrayList<>();

    private RecyclerView recyclerView;
    private TextView typingIndicatorTV;

    private Button ansButton1;
    private Button ansButton2;
    private Button ansButton3;
    private Button ansButton4;

    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);

        initializeQuestions();

        recyclerView = findViewById(R.id.recyclerview);
        typingIndicatorTV = findViewById(R.id.typingStatusTV);

        chatAdapter = new ChatAdapter(this, messages);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new Thread() {
            @Override
            public void run() {
                startTyping(new Message("Твое предложение еще в силе по поводу теста по Java?))", null, 0, false));
                startTyping(new Message("Отлично, скоро кину первое задание!)", null, 0, false));
                startTyping(new Message("awdwdw", R.drawable.q_10, 0, true));
                startTyping(new Message("1!", R.drawable.q_4, 0, true));
                startTyping(new Message("2!", R.drawable.q_5, 0, true));
                startTyping(new Message("3!", R.drawable.q_6, 0, true));
                startTyping(new Message("4!", R.drawable.q_8, 0, true));
                startTyping(new Message("5", R.drawable.q_9, 0, true));
                startTyping(new Message("6", R.drawable.q_10, 0, true));

            }
        }.start();
    }

    private void startTyping(Message msg) {
        int typingTime = 1;
        int indicatorDotsCount = 0;
        while(typingTime <= 5) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            indicatorDotsCount = indicatorDotsCount % 3 + 1;
            String indicatorText = "Typing";
            for(int i = 0; i < indicatorDotsCount; i++) {
                indicatorText += ".";
            }
            typingTime++;
            final String res = indicatorText;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    typingIndicatorTV.setText(res);
                }
            });
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                typingIndicatorTV.setText("");
            }
        });
        messages.add(msg);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                 chatAdapter.notifyItemInserted(messages.size()-1);
//                chatAdapter.notifyDataSetChanged();
//                recyclerView.getLayoutManager().scrollToPosition(messages.size()-1);
            }
        });
    }

    private void initializeQuestions() {
        questions.add(new Question("Поддерживает ли язык Java множественное наследование?", -1, new String[]{"Да", "Нет", "Неизвестно"}, "Нет"));
        questions.add(new Question("Результат выражения 1.0/0?", -1, new String[]{"Выдаст ошибку компиляции", "Проработает успешно", "Выдаст ArithmeticException"}, "Проработает успешно"));
        questions.add(new Question("Что верно для классов StringBuffer и StringBuilder?", -1, new String[]{"Методы StringBuilder синхронизированы", "Методы StringBuffer синхронизированы", "Оба класса синхронизированы", "Оба класса асинхронны"}, "Методы StringBuffer синхронизированы"));
        questions.add(new Question("Что выведет программа?", R.drawable.q_4, new String[]{"11", "0", "1", "Ошибка"}, "11"));
        questions.add(new Question("Что выведет программа?", R.drawable.q_5, new String[]{"1", "2", "3", "4"}, "2"));
        questions.add(new Question("Что выведет программа?", R.drawable.q_6, new String[]{"Static method called", "CompileTimeError", "RuntimeError"}, "Static method called"));
        questions.add(new Question("Является ли Java 100% объектно-ориентированным ЯП?", -1, new String[]{"Да", "Нет", "Неизвестно"}, "Нет"));
        questions.add(new Question("Что выведет программа?", R.drawable.q_8, new String[]{"Ошибка", "Ничего", "0", "1"}, "Ошибка"));
        questions.add(new Question("Что выведет программа?", R.drawable.q_9, new String[]{"Ошибка", "Ничего", "0", "1"}, "Ошибка"));
        questions.add(new Question("Что выведет программа?", R.drawable.q_10, new String[]{"True True", "True False", "False True", "False False"}, "False True"));
    }

}
