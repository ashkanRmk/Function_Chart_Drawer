package com.example.smn.functioncalculator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.mariuszgromada.math.mxparser.Expression;

import java.util.Stack;
import java.util.*;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {


    Spinner spinner;
    Button btn_var, btn_add_const, btn_const;
    EditText Ed_func_var, Ed_func_name, Ed_min, Ed_max, Ed_resulotion, Ed_Cons_name, Ed_Cons_value;

    TextView Tv_func;

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btn_dot, btn_not;
    Button btn_cos, btn_sin, btn_arctan, btn_abs, btn_exp, btn_tan, btn_cot, btn_log, btn_rev, btn_tavan, btn_clear, btn_back;
    Button btn_add, btn_minus, btn_mult, btn_div;
    Button btn_pbaz, btn_pbaste, btn_brabaz, btn_brabaste;

    private ProgressDialog progressBar;
    private Handler handler;

    Double resulotion, min, max;
    String func_name, fun_var, func_Value;

    int count = 0;

    List<String> Const_List = new LinkedList<>();
    List<Double> Const_Value = new LinkedList<>();


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            Log.d("Comments", "First time");
            Intent intent = new Intent(MainActivity.this, IntroActivity.class);
            startActivity(intent);
            settings.edit().putBoolean("my_first_time", false).commit();
        }


        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("BYekan.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


        //////Spinner
        spinner = (Spinner) findViewById(R.id.spin_const);

        ArrayAdapter<String> Con_lis = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, Const_List);
        Con_lis.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(Con_lis);
        /////////////////////


        ///////////Button
        btn_var = (Button) findViewById(R.id.btn_var);
        btn_add_const = (Button) findViewById(R.id.btn_con_add);
        btn_const = (Button) findViewById(R.id.btn_cons);
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn_dot = (Button) findViewById(R.id.btn_dot);
        btn_sin = (Button) findViewById(R.id.btn_sin);
        btn_cos = (Button) findViewById(R.id.btn_cos);
        btn_tan = (Button) findViewById(R.id.btn_tan);
        btn_cot = (Button) findViewById(R.id.btn_cot);
        btn_arctan = (Button) findViewById(R.id.btn_arctan);
        btn_exp = (Button) findViewById(R.id.btn_exp);
        btn_abs = (Button) findViewById(R.id.btn_abs);
        btn_log = (Button) findViewById(R.id.btn_log);
        btn_rev = (Button) findViewById(R.id.btn_qarine);
        btn_tavan = (Button) findViewById(R.id.btn_tavan);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_add = (Button) findViewById(R.id.add);
        btn_minus = (Button) findViewById(R.id.minus);
        btn_mult = (Button) findViewById(R.id.multy);
        btn_div = (Button) findViewById(R.id.div);
        btn_pbaz = (Button) findViewById(R.id.pbaz);
        btn_pbaste = (Button) findViewById(R.id.pbaste);
        btn_brabaz = (Button) findViewById(R.id.brbaz);
        btn_brabaste = (Button) findViewById(R.id.brbaste);
//        btn_not = (Button) findViewById(R.id.btn_not);
        ///////////////////

        Tv_func = (TextView) findViewById(R.id.Tv_Func);

        ///////////Edit Text
        Ed_func_var = (EditText) findViewById(R.id.Ed_Var);
        Ed_func_name = (EditText) findViewById(R.id.Ed_func_name);
        Ed_min = (EditText) findViewById(R.id.Ed_min);
        Ed_max = (EditText) findViewById(R.id.Ed_max);
        Ed_resulotion = (EditText) findViewById(R.id.Ed_resulotion);
        Ed_Cons_name = (EditText) findViewById(R.id.Ed_cons_name);
        Ed_Cons_value = (EditText) findViewById(R.id.Ed_cons_value);
        /////////////


        /////////Button Click Listioner
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "0");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "1");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "2");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "3");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "4");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "5");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "6");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "7");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "8");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "9");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = Tv_func.getText().toString();
                if (TextUtils.isEmpty(tmp)) {
                    Tv_func.setText("");
                } else {
                    Tv_func.setText(Tv_func.getText() + ".");
                }
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = true;
                String tmp = Tv_func.getText().toString();
                if (TextUtils.isEmpty(tmp)) {
                    Snackbar.make(view, "محتوای تابع خالی می باشد", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    check = false;
                }
                if (check) {
                    Tv_func.setText("");
                }
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = Tv_func.getText().toString();
                if (TextUtils.isEmpty(tmp)) {
                    Tv_func.setText("");
                } else {
                    Tv_func.setText(Tv_func.getText() + "+");
                }
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "-");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_mult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = Tv_func.getText().toString();
                if (TextUtils.isEmpty(tmp)) {
                    Tv_func.setText("");
                } else {
                    Tv_func.setText(Tv_func.getText() + "*");
                }
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = Tv_func.getText().toString();
                if (TextUtils.isEmpty(tmp)) {
                    Tv_func.setText("");
                } else {
                    Tv_func.setText(Tv_func.getText() + "/");
                }
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_pbaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "(");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_pbaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + ")");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_brabaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "floor(");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_brabaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "ceil(");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "sin(");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_cos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "cos(");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_tan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "tan(");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_cot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "cot(");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_arctan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "arctan(");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "log2(");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_abs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "abs(");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "exp(");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_tavan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = Tv_func.getText().toString();
                if (TextUtils.isEmpty(tmp)) {
                    Tv_func.setText("");
                } else {
                    Tv_func.setText(Tv_func.getText() + "^");
                }
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });
        btn_rev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_func.setText(Tv_func.getText() + "log10(");
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = true;
                String tmp = Tv_func.getText().toString();
                if (TextUtils.isEmpty(tmp)) {
                    Snackbar.make(view, "محتوای تابع خالی می باشد", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    check = false;
                }
                if (check) {
                    tmp = tmp.substring(0, tmp.length() - 1);
                    Tv_func.setText(tmp);
                }
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });

        btn_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = true;
                String tmp;
                tmp = Ed_func_var.getText().toString();
                if (TextUtils.isEmpty(tmp)) {
                    Snackbar.make(view, "متغیر تابع را وارد کنید", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    check = false;
                }
                if (check) {
                    btn_var.setText(tmp);
                    Tv_func.setText(Tv_func.getText() + tmp);
                }
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);
            }
        });

        btn_add_const.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean flag = true;

                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(50);

                if (TextUtils.isEmpty(Ed_Cons_name.getText().toString())) {
                    Snackbar.make(view, "لطفا مقادیر ثابت را وارد کنید!", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    flag = false;
                }

                if (flag) {
                    try {
                        Const_List.add(Ed_Cons_name.getText().toString());
                        Const_Value.add(Double.parseDouble(Ed_Cons_value.getText().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        flag = false;
                        Snackbar.make(view, "مقادیر ثابت نامعتبر می باشد", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                }

                if (flag) {
                    Snackbar.make(view, "مقدار ثابت ذخیره شد", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    ArrayAdapter<String> Con_lis = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, Const_List);
                    Con_lis.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinner.setAdapter(Con_lis);
                    Ed_Cons_name.setText("");
                    Ed_Cons_value.setText("");
                    count++;
                }

                //Hide Keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            }
        });


        btn_const.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Const_List.isEmpty())
                {
                    String tmp = Const_List.get(spinner.getSelectedItemPosition());
                    Tv_func.setText(Tv_func.getText() + tmp);
                    Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(25);
                }
                else {
                    Snackbar.make(view, "لیست ثابت ها خالی می باشد!", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(25);
                }
            }
        });
        ///////////////////////////


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = false;

                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(50);

                try {
                    resulotion = Double.parseDouble(Ed_resulotion.getText().toString());
                    min = Double.parseDouble(Ed_min.getText().toString());
                    max = Double.parseDouble(Ed_max.getText().toString());
                    func_name = Ed_func_name.getText().toString();
                    fun_var = Ed_func_var.getText().toString();
                    func_Value = Tv_func.getText().toString();
                    check = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    Snackbar.make(view, "مقادیر نامعتبر می باشند", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
                if (TextUtils.isEmpty(func_name)) {
                    Ed_func_name.setError("نام تابع را وارد کنید");
                    check = false;
                }
                if (TextUtils.isEmpty(fun_var)) {
                    Ed_func_var.setError("متغیر تابع را وارد کنید");
                    check = false;
                }
                if (TextUtils.isEmpty(func_Value)) {
                    Snackbar.make(view, "مقدار تابع خالی می باشد", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    check = false;
                }

                if (check) {
                    if (!normal_operator(func_Value)) {
                        Snackbar.make(view, "محتوای نامعتبر در مقدار تابع!", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        check = false;
                    }
                    if (!isBalanced(func_Value)) {
                        Snackbar.make(view, "مشکل در وارد کردن پرانتزها!", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        check = false;
                    }
                    if (min > max || resulotion > (max - min))
                    {
                        Snackbar.make(view, "مشکل در وارد کردن مقادیر کمینه یا دقت تابع!", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        check = false;
                    }

                    int index = 0;
                    for (String s : Const_List) {
                        if (func_Value.contains(s)) {
                            func_Value = func_Value.replace(s, Double.toString(Const_Value.get(index)));
                        }
                        index++;
                    }

                    if (Div_Zero(func_Value))
                    {
                        Snackbar.make(view, "خطای تقسیم بر صفر!", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        check = false;
                    }
                }


                progressBar = new ProgressDialog(view.getContext());

                progressBar.setCancelable(false);
                progressBar.setMessage("در حال محاسبه نتیجه تابع و رسم نمودار");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                if (check) {

                    progressBar.show();

                    new Thread(new Runnable() {
                        public void run() {

                            func_Value = change_symbol(func_Value); //*****************

                            List<Double> result_func = evaluation(func_Value, fun_var, min, max, resulotion); //**************
                            List<Double> var_array = var_array(min, max, resulotion);

                            double[] data1 = toPrimitive(result_func);
                            double[] data2 = toPrimitive(var_array);

                            Intent intent = new Intent(MainActivity.this, Chart_Activity.class);
                            intent.putExtra("result", data1);
                            intent.putExtra("var", data2);
                            intent.putExtra("fname", func_name);
                            intent.putExtra("fval", fun_var);

                            handler.sendEmptyMessage(0);

                            startActivity(intent);
                        }
                    }).start();
                    handler = new Handler() {
                        public void handleMessage(android.os.Message msg) {
                            progressBar.dismiss();
                        }
                    };
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, About_Activity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.help) {
            Intent intent1 = new Intent(MainActivity.this, IntroActivity.class);
            startActivity(intent1);
            return true;
        }
        if (id == R.id.exit) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "برای خروج دوباره کلیک کنید", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    //Custom Functions
    public static boolean isBalanced(String input) {
        Stack<Character> st = new Stack<>();
        for (char chr : input.toCharArray()) {
            switch (chr) {
                case '(':
                    st.push(chr);
                    break;
                case ')':
                    if (st.isEmpty() || st.pop() != '(')
                        return false;
                    break;
            }
        }
        return st.isEmpty();
    }

    public boolean normal_operator(String input) {
        String operator = "+-*/^.";
        String tmp;
        for (char chr1 : operator.toCharArray()) {
            for (char chr2 : operator.toCharArray()) {
                tmp = new StringBuilder().append(chr1).append(chr2).toString();
                if (input.contains(tmp)) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Double> evaluation(String func, String vr, Double min, Double max, Double res) {
        int index = 0;
        List<Double> result = new LinkedList<>();
        if (func.contains(vr)) {
            for (Double var = min; var <= max; var += res, index++) {
                String tmp = func;
                tmp = tmp.replace(vr, Double.toString(var));
                Expression e = new Expression(tmp);
                result.add(e.calculate());
            }
        } else {
            for (Double var = min; var <= max; var += res, index++) {
                Expression e = new Expression(func);
                result.add(e.calculate());

            }
        }
        return result;
    }

    public List<Double> var_array(Double min, Double max, Double res) {
        int index = 0;
        List<Double> result = new LinkedList<>();
        for (Double var = min; var <= max; var += res, index++) {
            result.add(var);
        }
        return result;
    }

    public double[] toPrimitive(List<Double> array) {
        if (array == null) {
            return null;
        } else if (array.size() == 0) {
            return null;
        }

        final double[] result = new double[array.size()];
        for (int i = 0; i < array.size(); i++) {
            result[i] = array.get(i);
        }
        return result;
    }

    public boolean Div_Zero(String func)
    {
        if (func.contains("/0"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public String change_symbol(String func)
    {
        func = func.replaceAll("sin","#");
        func = func.replaceAll("cos","$");
        func = func.replaceAll("tan","@");
        func = func.replaceAll("cot","%");
        func = func.replaceAll("abs","`");

        return func;
    }


    public List<Double> evaluation_m(String func, String vr, Double min, Double max, Double res) {
        int index = 0;
        List<Double> result = new LinkedList<>();
        if (func.contains(vr)) {
            for (Double var = min; var <= max; var += res, index++) {
                String tmp = func;
                tmp = tmp.replace(vr, Double.toString(var));
                EvaluateString e = new EvaluateString();
                e.evaluate(tmp);
                result.add(e.evaluate(tmp));
            }
        } else {
            for (Double var = min; var <= max; var += res, index++) {
                Expression e = new Expression(func);
                result.add(e.calculate());

            }
        }
        return result;
    }


}




