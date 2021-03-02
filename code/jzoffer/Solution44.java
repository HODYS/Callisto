package code.jzoffer;

//1st attempt
//1.大数问题，虽然n是int，但是total是可能越界的啊，比如上界是500，卡在3位数，total是2700越界了。
//2.if(left >= total)的else可以封装一下
//3.findDigit用while写会比用for多判断几次

class Solution44 {
    public int findNthDigit(int n) {
        if(n < 0){
            return -1;
        }

        int curdigit = 1;
        int left = n;//how many digits are left

        while(left >= 0){
            long total = digitCount(curdigit) * curdigit;
            if(left >= total)
                left -= total;
            else{
                int number = 0;
                if(curdigit > 1)
                    number = left / curdigit + (int)Math.pow(10,curdigit - 1);
                else
                    number = left / curdigit;
                int digit = left % curdigit;
                return findDigit(number,  curdigit - 1 - digit); // reverse

            }
            curdigit ++;

        }

        return -1;

    }

    public int digitCount(int digit){ //count how many number is of this digit
        if(digit == 1)
            return 10 * digit;
        return (int)(Math.pow(10, digit - 1)) * 9;
    }

    public int findDigit(int number, int digit){
        int count = 0;
        while(number > 0){
            if(count == digit)
                return number % 10;
            number /= 10;
            count ++;
        }
        return -1;
    }
}




