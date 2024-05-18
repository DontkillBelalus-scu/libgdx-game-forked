#ifdef GL_FRAGMENT_PRECISION_HIGH
precision highp float;
#else
precision mediump float;
#endif

varying vec2 f_coord;
uniform vec2 resolution;
uniform float time;
uniform vec2 pos;

float random(vec2 _st)
{
    return fract(sin(dot(_st.xy,
                         vec2(0.630, 0.710))) *
        43759.329);
}

//柏林噪声
float noise(vec2 _st)
{
    vec2 i = floor(_st);
    vec2 f = fract(_st);


    float a = random(i);
    float b = random(i + vec2(1.0, 0.0));
    float c = random(i + vec2(0.0, 1.0));
    float d = random(i + vec2(1.0, 1.0));

    vec2 u = f * f * (3.0 - 2.0 * f);

    return mix(a, b, u.x) +
            (c - a) * u.y * (1.0 - u.x) +
            (d - b) * u.x * u.y;
}

#define NUM_OCTAVES 1

//布朗分形
float fbm(vec2 _st)
{
    float v = 0.0;
    float a = 0.5;
    vec2 shift = vec2(100.0, 100.0);
    mat2 rot = mat2(cos(0.5), sin(0.5),
                    -sin(0.5), cos(0.50));

    for (int i = 0; i < NUM_OCTAVES; ++i)
    {
        v += a * noise(_st);
        //_st = mul(_st, rot) * 2.0 + shift;
        _st = _st*rot * 2.0 + shift;
        a *= 0.5;
    }
    return v;
}

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    vec2 uv = gl_FragCoord.xy/resolution.xy;
    uv.y = 1.0 - uv.y;


    vec2 st = uv * 10.0f+pos/resolution;
    st.x += time * 0.1f;
    st.y -= time * 0.2f;
    // st += st * abs(sin(u_time*0.1)*3.0);
    vec3 color;

    vec2 q;
    q.x = fbm(st + 0.00 * time*5.0);
    q.y = fbm(st + vec2(1.0, 1.0));

    vec2 r;
    r.x = fbm(st + 1.0 * q + vec2(1.7, 9.2) + 0.15 * time*5.0);
    r.y = fbm(st + 1.0 * q + vec2(8.3, 2.8) + 0.126 * time*5.0);

    float f = fbm(st + r);

    color = mix(vec3(1.0, 1.0, 1.0),
                vec3(1.0, 1.0, 1.0),
                clamp((f * f) * 4.0, 0.0, 1.0));

    color = mix(color,
                vec3(1.0, 1.0, 1.0),
                clamp(length(q), 0.0, 1.0));

    color = mix(color,
                vec3(1.0, 1.0, 1.0),
                clamp(length(r.x), 0.0, 1.0));

    vec3 cloud = vec3((f * f * f + 0.3 * f * f + 0.5 * f) * color);

    //cloud = mix(vec3(0.0f, 0.0f, 0.0f), cloud, uv.y);

    fragColor = vec4(cloud, 1.);
}

void main(void) {
	vec4 fragment_color;
	mainImage(fragment_color, f_coord.xy);
	gl_FragColor=vec4(fragment_color.rgbr);
}