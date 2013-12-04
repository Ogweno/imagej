#!/bin/sh

cd "$(dirname "$0")/.." &&
if test ! -d app/ImageJ.app
then
	mvn -Ppopulate-app "$@"
else
	mvn "$@" &&
	VERSION="$(sed -n 's/^.<version>\(.*\)<\/version>.*/\1/p' < pom.xml)" &&
	(cd app &&
	 ZIP=target/imagej-$VERSION-application.zip &&
	 unzip -o $ZIP &&
	 contents="$(unzip -l $ZIP | sed -n 's/^.*\(ImageJ.app\/.*[^\/]\)$/\1/p')" &&
	 possibly_obsoletes="$(printf "%s\n%s\n%s" \
			"$contents" "$contents" "$(find ImageJ.app -type f)" |
		grep -ve /.checksums$ -e /db.xml.gz \
			-e /tools-1.4.2.jar$ -e /ImageJ2.desktop$ |
		sort | uniq -u)" &&
	 if test -n "$possibly_obsoletes"
	 then
		printf "The following files might be outdated:\n\n%s\nDo you want to delete them? " \
			"$possibly_obsoletes" &&
		read line &&
		case "$line" in
		y*|Y*)
			echo "$possibly_obsoletes" |
			tr '\n' '\0' |
			xargs -0 rm
			;;
		esac
	 fi)
fi &&
for jar in app/ImageJ.app/jars/*.jar
do
	if test -n "$(unzip -p $jar \*pom.properties 2> /dev/null |
		grep "^#Generated by" | grep Eclipse)"
	then
		echo "WARNING: built by Eclipse: $jar"
	fi
done
