#!/bin/sh

userresources=$HOME/.Xresources
usermodmap=$HOME/.Xmodmap
sysresources=/etc/X11/xinit/.Xresources
sysmodmap=/etc/X11/xinit/.Xmodmap

XDG_CONFIG_HOME=$HOME/.config

export EDITOR=/usr/bin/vim

# merge in defaults and keymaps

if [ -f $sysresources ]; then
    xrdb -merge $sysresources
fi

if [ -f $sysmodmap ]; then
    xmodmap $sysmodmap
fi

if [ -f "$userresources" ]; then
    xrdb -merge "$userresources"
    xrdb -merge $HOME/.Xresources_light
fi

if [ -f "$usermodmap" ]; then
    xmodmap "$usermodmap"
fi

# start some nice programs

if [ -d /etc/X11/xinit/xinitrc.d ] ; then
	for f in /etc/X11/xinit/xinitrc.d/* ; do
		[ -x "$f" ] && . "$f"
	done
	unset f
fi

#####

# background
cat ~/.bg | xargs -0 feh --bg-fill &

#fuck you beep
xset -b
#cursor speed & pic
xset m 6/1 1
xsetroot -cursor_name left_ptr

urxvtd -q -o -f 
urxvtc &

# screensaver
/usr/bin/xscreensaver -no-splash &

# music
mpd &

# change background for next session
find ~/hd/img/wall/ -type f \( -name '*.jpg' -o -name '*.png' \) -print0 | shuf -n1 -z > ~/.bg
BGI=$(cat ~/.bg)
rm ~/.bgi
ln -s $BGI ~/.bgi

exec xmonad
#exec i3 
