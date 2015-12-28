

[[ $- != *i* ]] && return

# http://ruderich.org/simon/config/zshrc
umask 007
setopt autocd
autoload -Uz zmv
setopt histignorealldups
setopt histignorespace
autoload -Uz colors && colors
bindkey -v
bindkey '\e[A' history-beginning-search-backward
bindkey '\e[B' history-beginning-search-forward

# Alias

alias fl='ranger'
alias k='clear && mathomatic -q'
alias subl='subl3'
alias feh='feh -FdZ'

alias dir2tar='find . -type d -maxdepth 1 -mindepth 1 -exec tar cvf {}.tar {}  \;'
alias tar2pgp='find . -type f -name \*.tar -maxdepth 1 -exec gpg --encrypt -r Thiago -o {}.gpg {} \; -exec rm {} \;'
alias dec='gpg --decrypt -r Thiago -o'
alias enc='gpg --encrypt -r Thiago -o'
alias gpg2tar='find . -type f -name \*.gpg -maxdepth 1 -exec gpg --decrypt -r Thiago -o {}.tar {} \;'


alias ls='ls -hF --color=auto'
alias ll='ls -l'
alias la='ll -A'

alias ..='cd .. && ls'
alias ...='cd ../.. && ls'
alias lk='cd'
alias grep='grep --color=auto'
alias mkdir='mkdir -p -v'
alias ping='ping -c 3'
alias :q=' exit'
alias :Q=' exit'
alias :x=' exit'
alias cd..='cd ..'

alias cp='cp -i'
alias mv='mv -i'
alias rm='rm -I'
alias ln='ln -i'
alias chown='chown --preserve-root'
alias chmod='chmod --preserve-root'
alias chgrp='chgrp --preserve-root'
alias cls=' echo -ne "\033c"'

alias shut='systemctl poweroff'
alias shutdown='systemctl poweroff'
alias reboot='systemctl reboot'

alias fb="printf '\33]50;%s\007' 'xft:Inconsolata:pixelsize=26:antialias=true:hinting=true'"
alias fm="printf '\33]50;%s\007' 'xft:Inconsolata:pixelsize=12:antialias=true:hinting=true'"
alias fs="printf '\33]50;%s\007' 'xft:Inconsolata:pixelsize=8:antialias=true:hinting=true'"

# faculdade

alias todo="vim /mnt/w7/Users/thi/Desktop/todo.txt"
alias debt="vim /mnt/w7/Users/thi/Desktop/debts.txt"

## ex - archive extractor
# usage: ex <file>
function ex() {
  if [ -f $1 ] ; then
    case $1 in
      *.tar.bz2|*.tbz2) tar xjf $1 ;;
      *.tar.gz)         tar xzf $1   ;;
      *.bz2)            bunzip2 $1   ;;
      *.rar)            unrar e $1   ;;
      *.gz)             gunzip $1    ;;
      *.tar)            tar xf $1    ;;
      *.tgz)            tar xzf $1   ;;
      *.zip)            unzip $1     ;;
      *.Z)              uncompress $1;;
      *.7z)             7z x $1      ;;
      *)                echo "'$1' cannot be extracted via ex" ;;
    esac
  else
    echo "'$1' is not a valid file"
  fi
}


# TEMA

sublrc=$HOME/.config/sublime-text-3/Packages/User/Preferences.sublime-settings
conkyrc=$HOME/.conkyrc
tmprc=$HOME/.tmprc
gtkrc=$HOME/.gtkrc-2.0
cssff=$HOME/.mozilla/firefox/20xi8kg1.default/chrome/userChrome.css
function light() {
  xrdb -load $HOME/.Xresources
  xrdb -merge $HOME/.Xresources_light
  
  echo "$(cat $sublrc | sed -r "s/\(Dark\)\.tmTheme/(Light).tmTheme/")" > $sublrc
  echo "$(cat $gtkrc | sed -r "s/dark/light/")" > $gtkrc

  cat $cssff | sed -r "s/002b36/fdf6e3/" > $cssff.tmp
  cat $cssff.tmp > $cssff
  cat $cssff | sed -r "s/586e75/93a1a1/" > $cssff.tmp
  cat $cssff.tmp > $cssff
  cat $cssff | sed -r "s/white/black/" > $cssff.tmp
  cat $cssff.tmp > $cssff
  rm $cssff.tmp

  cat $conkyrc| sed -r "s/own_window_colour 002b36/own_window_colour fdf6e3/" > $conkyrc.tmp
  cat $conkyrc.tmp > $conkyrc
  cat $conkyrc | sed -r "s/color #839496/color #657b83/" > $conkyrc.tmp
  cat $conkyrc.tmp > $conkyrc
  cat $conkyrc | sed -r "s/default_shade_color 073642/default_shade_color eee8d5/" > $conkyrc.tmp
  cat $conkyrc.tmp > $conkyrc
  rm $conkyrc.tmp

  xmonad --restart
  urxvtc &! exit
}


function dark() {
  xrdb -load $HOME/.Xresources
  xrdb -merge $HOME/.Xresources_dark
  
  echo "$(cat $sublrc | sed -r "s/\(Light\)\.tmTheme/(Dark).tmTheme/")" > $sublrc
  echo "$(cat $gtkrc | sed -r "s/light/dark/")" > $gtkrc

  cat $cssff | sed -r "s/fdf6e3/002b36/" > $cssff.tmp
  cat $cssff.tmp > $cssff
  cat $cssff | sed -r "s/93a1a1/586e75/" > $cssff.tmp
  cat $cssff.tmp > $cssff
  cat $cssff | sed -r "s/black/white/" > $cssff.tmp
  cat $cssff.tmp > $cssff
  rm $cssff.tmp

  cat $conkyrc| sed -r "s/own_window_colour fdf6e3/own_window_colour 002b36/" > $conkyrc.tmp
  cat $conkyrc.tmp > $conkyrc
  cat $conkyrc | sed -r "s/color #657b83/color #839496/" > $conkyrc.tmp
  cat $conkyrc.tmp > $conkyrc
  cat $conkyrc | sed -r "s/default_shade_color eee8d5/default_shade_color 073642/" > $conkyrc.tmp
  cat $conkyrc.tmp > $conkyrc
  rm $conkyrc.tmp
  
  xmonad --restart
  urxvtc &! exit
}


# SE SAIR, VOLTAR AO LOGIN DO THI


# if [[ -z "$DISPLAY" ]] && [[ $(tty) = /dev/tty1 ]]; then
#   startx
#   logout
# fi


# Converte um video para mp3
function mp3 () {
  ffmpeg -threads 8 -i "$1" -q:a 0 -map a "${1}.mp3"
}


# PROMPT

if [ $UID -eq 0 ]; then NCOLOR="red"; else NCOLOR="green"; fi
local return_code=" %(?. .%{$fg[red]%} %? %{$reset_color%})"

local THI_COL=$(( COLUMNS / 2 ))
if [[ $COLUMNS < 46 ]] ; then
  THI_COL=$(( ( COLUMNS - 12 ) / 2 ))
fi

PROMPT='%{$FG[105]%}%(!.#.»)%{$reset_color%} '
RPROMPT='$(git_prompt_info)${return_code}$FG[032]%$THI_COL<..<${PWD/#$HOME/~}%<<%{$reset_color%}'

ZSH_THEME_GIT_PROMPT_DIRTY="%{$fg[yellow]%}⚡%{$reset_color%}"
ZSH_THEME_GIT_PROMPT_PREFIX="%{$fg[green]%}"
ZSH_THEME_GIT_PROMPT_SUFFIX="%{$reset_color%} "
