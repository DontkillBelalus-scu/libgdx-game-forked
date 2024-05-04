varying vec2 f_coord;
attribute vec2 coord;
attribute vec2 a_position;
void main(){
  f_coord=coord;
  gl_Position = vec4(a_position.xy,0.,1.);
}