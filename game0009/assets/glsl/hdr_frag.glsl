#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
vec4 hdr(vec3 mcolor)
{
    const float gamma = 2.2;
    vec3 hdrColor = mcolor.rgb;    // Reinhard色调映射
    vec3 mapped = hdrColor / (hdrColor + vec3(1.0));
    // Gamma校正
    mapped = pow(mapped, vec3(1.0 / gamma));

    vec4 color = vec4(mapped, 1.0);
    return color;
}
void main(){
        vec2 v_uv=v_texCoords;
        float radius = 0.0005;//描边的宽度
        vec3 u_outlineColor = vec3(1.0,255.0,1.0);//描边的颜色
        float u_threshold = 0.8;//描边的模糊度
        vec4 accum = vec4(0.0);
        vec4 normal = vec4(0.0);
        normal = texture2D(u_texture, vec2(v_uv.x, v_uv.y));
        
        accum += texture2D(u_texture, vec2(v_uv.x - radius, v_uv.y - radius));//左下一个点
        accum += texture2D(u_texture, vec2(v_uv.x + radius, v_uv.y - radius));//右下一个点
        accum += texture2D(u_texture, vec2(v_uv.x + radius, v_uv.y + radius));//右上一个点
        accum += texture2D(u_texture, vec2(v_uv.x - radius, v_uv.y + radius));//左上一个点
        accum *= u_threshold;//模糊度
        accum.rgb =  u_outlineColor * accum.a;
        //accum.a=0.0;
        //下面这个公式就是判断当前的正方形中心区域不能落在可以显示有颜色的像素区域里
        normal = ( accum * (1.0-normal.a)) + (normal * normal.a);
        gl_FragColor = vec4(normal.rgb,normal.a);
}