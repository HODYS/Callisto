# grep
grep options：
 + -c, --count  
Suppress normal output; instead print a count of matching lines
for each input file.  With the -v, --invert-match  option  (see
below), count non-matching lines.

+ -w, --word-regexp  
Select  only  those  lines  containing  matches that form whole
words.  The test is that the matching substring must either  be
at  the  beginning  of  the  line,  or  preceded  by a non-word
constituent character.  Similarly, it must be either at the end
of  the  line  or followed by a non-word constituent character.
Word-constituent  characters  are  letters,  digits,  and   the
underscore.  This option has no effect if -x is also specified.

+ -i, --ignore-case
Ignore case distinctions, so that characters that  differ  only
in case match each other.


+ -A NUM, --after-context=NUM  
Print  NUM  lines  of  trailing  context  after matching lines.
Places  a  line  containing  a  group  separator  (--)  between
contiguous  groups  of matches. 

  ## Perl正则表达式
  通过-P开启

  word boundaries（单词边界）


  ## sort
-c, --check, --check=diagnose-first
check for sorted input; do not sort



-u: with -c, check for strict ordering; without -c, output only the
first of an equal run