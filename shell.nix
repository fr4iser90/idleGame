{ pkgs ? import <nixpkgs> {} }:
 # run with nix-shell --run "mvn javafx:run"
pkgs.mkShell {
  buildInputs = [
    pkgs.jdk17
    pkgs.maven
    pkgs.javaPackages.openjfx17
    pkgs.libGL
    pkgs.libGLU
    pkgs.gtk3
    pkgs.xorg.libX11
    pkgs.xorg.libXext
    pkgs.xorg.libXxf86vm
    pkgs.xorg.libXtst
    pkgs.xorg.libXrender
    pkgs.glib
    pkgs.cairo
    pkgs.pango
    pkgs.atk
    pkgs.gdk-pixbuf
    pkgs.freetype
    pkgs.fontconfig
  ];

  shellHook = ''
    export JAVA_HOME=${pkgs.jdk17}
    export MAVEN_OPTS="-Djavafx.platform=linux"
    export LD_LIBRARY_PATH=${pkgs.lib.makeLibraryPath [
      pkgs.jdk17
      pkgs.maven
      pkgs.javaPackages.openjfx17 
      pkgs.libGL
      pkgs.libGLU
      pkgs.gtk3
      pkgs.xorg.libX11
      pkgs.xorg.libXext
      pkgs.xorg.libXxf86vm
      pkgs.xorg.libXtst
      pkgs.xorg.libXrender
      pkgs.glib
      pkgs.cairo
      pkgs.pango
      pkgs.atk
      pkgs.gdk-pixbuf
      pkgs.freetype
      pkgs.fontconfig
    ]}:$LD_LIBRARY_PATH
    export XDG_DATA_DIRS=${pkgs.gtk3}/share/gsettings-schemas/${pkgs.gtk3.name}:${pkgs.glib}/share/gsettings-schemas/${pkgs.glib.name}:$XDG_DATA_DIRS
  '';
}
