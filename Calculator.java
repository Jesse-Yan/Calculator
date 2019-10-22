import java.util.ArrayList;

public class Calculator {

  public static Double evaluate(String expression) {
    String[] spliter = expression.split(" ");
    Calculate cal = new Calculate(spliter);
    double result = cal.doCal();
    return result;
  }
}


class Calculate {
  private StackX theStackI;
  private StackY theStackP;
  private String[] input;
  private ArrayList<String> output;
  private double num1, num2, interAns;

  public Calculate(String[] s) {
    this.input = s;
    theStackP = new StackY(input.length);
    output = new ArrayList<String>();
  }

  public double doCal() {
    doTrans();
    doParse();
    return theStackI.pop();
  }
  
  public void doParse() {
    theStackI = new StackX(input.length);
    for (int i = 0; i < output.size(); i++) {
      String temp = output.get(i);
      if(!(temp.equals("+") || temp.equals("-") || temp.equals("*") || temp.equals("/"))) {
        theStackI.push(Double.parseDouble(temp));
      } else {
        num2 = theStackI.pop();
        num1 = theStackI.pop();
        switch(temp) {
          case "+":
            interAns = num1 + num2;
            break;
          case "-":
            interAns = num1 - num2;
            break;
          case "*":
            interAns = num1 * num2;
            break;
          case "/":
            interAns = num1 / num2;
            break;
          default:
            interAns = 0;
            break;
        }
        theStackI.push(interAns);
      }
    }
  }
  
  public void doTrans() {
    for (int i = 0; i < input.length; i++) {
      String temp = input[i];
      if(temp.equals("(")) {
        theStackP.push(temp);
      } else if(temp.equals("+") || temp.equals("-")) {
        goOper(temp, 1);
      } else if(temp.equals("*") || temp.equals("/")) {
        goOper(temp, 2);
      } else if(temp.equals(")")){
        goParen();
      } else {
        output.add(temp);
      }
    }

    while (!theStackP.isEmpty()) {
      output.add(theStackP.pop());
    }
  }

  private void goParen() {

    while (!theStackP.isEmpty()) {

      String s = theStackP.pop();
      if (s.equals("("))
        break;
      output.add(s);
    }

  }

  private void goOper(String temp, int i) {

    while (!theStackP.isEmpty()) {
      String s = theStackP.pop();
      if (s.equals("("))
        theStackP.push(s);
      else {
        int j;

        if (s.equals("+") || s.equals("-")) {
          j = 1;
        } else {
          j = 2;
        }

        if (j < i) {
          theStackP.push(s);
          break;
        } else {
          output.add(s);
        }
      }
    }
    theStackP.push(temp);
  }
}


class StackX {
  private int maxSize;
  private double[] stackArray;
  private int top;

  public StackX(int j) {
    this.maxSize = j;
    stackArray = new double[maxSize];
    top = -1;
  }

  public void push(double s) {
    stackArray[++top] = s;
  }

  public double pop() {
    return stackArray[top--];
  }

  public boolean isEmpty() {
    return top == -1;
  }
}


class StackY {
  private int maxSize;
  private String[] stackArray;
  private int top;

  public StackY(int j) {
    this.maxSize = j;
    stackArray = new String[maxSize];
    top = -1;
  }

  public void push(String s) {
    stackArray[++top] = s;
  }

  public String pop() {
    return stackArray[top--];
  }

  public boolean isEmpty() {
    return top == -1;
  }
}
