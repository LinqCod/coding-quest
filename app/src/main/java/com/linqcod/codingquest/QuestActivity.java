package com.linqcod.codingquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestActivity extends AppCompatActivity {

    private ArrayList<Message> messages = new ArrayList<>();
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<String> nextQuestionMsg = new ArrayList<>();
    private ArrayList<String> thanksMsg = new ArrayList<>();

    private int rightAnswers = 0;
    private Question currentQuestion;
    private int currentQuestionId;

    private RecyclerView recyclerView;
    private TextView typingIndicatorTV;

    private Button[] buttons = new Button[3];

    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);

        initializeQuestions();

        recyclerView = findViewById(R.id.recyclerview);
        typingIndicatorTV = findViewById(R.id.typingStatusTV);

        buttons[0] = findViewById(R.id.ansBtn1);
        buttons[1] = findViewById(R.id.ansBtn2);
        buttons[2] = findViewById(R.id.ansBtn3);

        chatAdapter = new ChatAdapter(this, messages);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        startQuest();
    }

    private void disableButtons() {
        buttons[0].setVisibility(View.GONE);
        buttons[1].setVisibility(View.GONE);
        buttons[2].setVisibility(View.GONE);
    }

    private void setUpButtons() {
        buttons[0].setVisibility(View.VISIBLE);
        buttons[1].setVisibility(View.VISIBLE);
        buttons[2].setVisibility(View.VISIBLE);
        buttons[0].setText(currentQuestion.getAnswers()[0]);
        buttons[1].setText(currentQuestion.getAnswers()[1]);
        buttons[2].setText(currentQuestion.getAnswers()[2]);
    }

    private void startQuest() {
        disableButtons();
        new Thread() {
            @Override
            public void run() {
                startTyping(new Message(questions.get(0).getQuestion(), questions.get(0).getImageUri(), 0, questions.get(0).isHasImage()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        buttons[0].setVisibility(View.VISIBLE);
                        buttons[1].setVisibility(View.VISIBLE);
                        buttons[0].setText(questions.get(0).getAnswers()[0]);
                        buttons[1].setText(questions.get(0).getAnswers()[1]);
                    }
                });
                buttons[0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendMessage(new Message("Разумеется! Я же обещал)", R.drawable.balvanka, 1, false));
                        disableButtons();
                        new Thread() {
                            @Override
                            public void run() {
                                startTyping(new Message("Спасибо огромное! Тогда скоро пришлю первое задание.", R.drawable.balvanka, 0, false));
                                //TODO: fix if 0 index -> arrayOutOfBound because 2 answers, not 3
                                askQuestion(currentQuestionId, "Так, вот первый вопрос: ", thanksMsg.get(currentQuestionId-1));
                            }
                        }.start();
                    }
                });
                buttons[1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

            }
        }.start();
    }

    private void askQuestion(int qIndex, final String firstStr, final String secondStr) {
        disableButtons();
        currentQuestion = questions.get(currentQuestionId);
        new Thread() {
            @Override
            public void run() {
                startTyping(new Message(firstStr + currentQuestion.getQuestion(), currentQuestion.getImageUri(), 0, currentQuestion.isHasImage()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setUpButtons();
                    }
                });
                buttons[0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendMessage(new Message(currentQuestion.getAnswers()[0], R.drawable.balvanka, 1, false));
                        if(currentQuestion.answer(currentQuestion.getAnswers()[0])) rightAnswers++;
                        disableButtons();
                        new Thread() {
                            @Override
                            public void run() {
                                startTyping(new Message(secondStr, R.drawable.balvanka, 0, false));
                                askQuestion(++currentQuestionId, "Так, вот следующий вопрос: ",  thanksMsg.get(currentQuestionId-1));
                            }
                        }.start();
                    }
                });
                buttons[1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendMessage(new Message(currentQuestion.getAnswers()[1], R.drawable.balvanka, 1, false));
                        if(currentQuestion.answer(currentQuestion.getAnswers()[1])) rightAnswers++;
                        disableButtons();
                        new Thread() {
                            @Override
                            public void run() {
                                startTyping(new Message(secondStr, R.drawable.balvanka, 0, false));
                                askQuestion(++currentQuestionId, "Так, вот следующий вопрос: ",  thanksMsg.get(currentQuestionId-1));
                            }
                        }.start();
                    }
                });
                buttons[2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendMessage(new Message(currentQuestion.getAnswers()[2], R.drawable.balvanka, 1, false));
                        if(currentQuestion.answer(currentQuestion.getAnswers()[2])) rightAnswers++;
                        disableButtons();
                        new Thread() {
                            @Override
                            public void run() {
                                startTyping(new Message(secondStr, R.drawable.balvanka, 0, false));
                                askQuestion(++currentQuestionId, "Так, вот следующий вопрос: ",  thanksMsg.get(currentQuestionId-1));
                            }
                        }.start();
                    }
                });
            }
        }.start();
    }

    private void startTyping(final Message msg) {
        int typingTime = 1;
        int indicatorDotsCount = 0;
        while(typingTime <= 6) {
            try {
                Thread.sleep(1000);
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sendMessage(msg);
            }
        });
    }


    private void sendMessage(Message msg) {
        messages.add(msg);
        chatAdapter.notifyItemInserted(messages.size()-1);
        recyclerView.getLayoutManager().scrollToPosition(messages.size()-1);
    }

    private void initializeQuestions() {
        currentQuestionId = 1;
        questions.add(new Question("Твое предложение еще в силе по поводу теста по Java?))", R.drawable.balvanka, false, new String[]{"Да", "Нет"}, "Да"));
        questions.add(new Question("Поддерживает ли язык Java множественное наследование?", R.drawable.balvanka,false, new String[]{"Да", "Нет", "Неизвестно"}, "Нет"));
        questions.add(new Question("Результат выражения 1.0/0?", R.drawable.balvanka,false, new String[]{"Выдаст ошибку компиляции", "Проработает успешно", "Выдаст ArithmeticException"}, "Проработает успешно"));
        questions.add(new Question("Что верно для классов StringBuffer и StringBuilder?", R.drawable.balvanka,false, new String[]{"Методы StringBuilder синхронизированы", "Методы StringBuffer синхронизированы", "Оба класса синхронизированы"}, "Методы StringBuffer синхронизированы"));
        questions.add(new Question("Что выведет программа?", R.drawable.q_4,true, new String[]{"11", "0", "Ошибка"}, "11"));
        questions.add(new Question("Что выведет программа?", R.drawable.q_5,true, new String[]{"1", "2", "3"}, "2"));
        questions.add(new Question("Что выведет программа?", R.drawable.q_6,true, new String[]{"Static method called", "CompileTimeError", "RuntimeError"}, "Static method called"));
        questions.add(new Question("Является ли Java 100% объектно-ориентированным ЯП?", R.drawable.balvanka,false, new String[]{"Да", "Нет", "Неизвестно"}, "Нет"));
        questions.add(new Question("Что выведет программа?", R.drawable.q_8,true, new String[]{"Ошибка", "Ничего", "0"}, "Ошибка"));
        questions.add(new Question("Что выведет программа?", R.drawable.q_9,true, new String[]{"Ошибка", "Ничего", "0"}, "Ошибка"));
        questions.add(new Question("Что выведет программа?", R.drawable.q_10,true, new String[]{"True True", "True False", "False True"}, "False True"));

        thanksMsg.add("Спасибо! Скоро кину некст вопрос");
        thanksMsg.add("Океюшки");
        thanksMsg.add("Спасибо большое! Ща след вопрос кину.");
        thanksMsg.add("Благодарствую!)");
        thanksMsg.add("Спасибо, надеюсь, что не отвлекаю))");
        thanksMsg.add("От души)");
        thanksMsg.add("Спасибо.");
        thanksMsg.add("Святой ты человек!)");
        thanksMsg.add("Капец, я вообще это не понимаю... Благодарю)");
        thanksMsg.add("Ты просто машина!) Посмотрим, что будет по итогу!");
    }

}
