#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;
uniform float LumiTransparency;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

uniform float GameTime;
uniform float Speed;
uniform float Distortion;
uniform float Width;
uniform float Height;
uniform vec4 UVCoordinates;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

float hash(vec2 p) {
    p = p - (512. * floor(p/512.));
    return fract(sin(dot(p, vec2(12.9898, 4.1414))) * 43758.5453);
}

float noise(vec2 x) {
    vec2 i = floor(x);
    vec2 f = fract(x);
    f = f*f*(3.0-2.0*f);

    float res =
    mix(
    mix(hash(i+vec2(0., 0.)),hash(i+vec2(1., 0.)),f.x),
    mix(hash(i+vec2(0., 1.)),hash(i+vec2(1., 1.)),f.x),f.y);
    return res*res;
}

float layeredNoise(vec2 uv) {
    float time = Speed*GameTime;
    time = time - (2048. * floor(time/2048.));
    //    Makes it sharp !!!
    //        vec2 direction = normalize(vec2(sin(uv.y-time*0.25), cos(uv.x-time*0.25)));
    vec2 direction = normalize(vec2(sin(time*0.025), cos(time*0.025)));
    vec2 offset = 3.*normalize(vec2(time, time)*direction);

    vec2 positive = uv+offset;
    vec2 negative = uv-offset;
    float n = 0.;
    n += 0.5*noise(positive);
    n += 0.25*noise(negative);
    n += 0.125*noise(positive);
    n += 0.0625*noise(negative);
    n += 0.03215*noise(positive);
    return n/2.;
}

float pattern(vec2 uv) {
    return layeredNoise(uv+layeredNoise(uv+layeredNoise(uv)));
}

vec4 transformColor(vec4 initialColor, float lumiTransparent) {
    initialColor = lumiTransparent == 1. ? vec4(initialColor.xyz, (0.21 * initialColor.r + 0.71 * initialColor.g + 0.07 * initialColor.b)) : initialColor;
    return initialColor * vertexColor * ColorModulator;
}

vec4 applyFog(vec4 initialColor, float fogStart, float fogEnd, vec4 fogColor, float vertexDistance) {
    return linear_fog(vec4(initialColor.rgb, initialColor.a*linear_fog_fade(vertexDistance, fogStart, fogEnd)), vertexDistance, fogStart, fogEnd, vec4(fogColor.rgb, initialColor.r));
}

void main() {
    vec2 uv = texCoord0;
    vec2 uCap = vec2(UVCoordinates.x, UVCoordinates.y);
    vec2 vCap = vec2(UVCoordinates.z, UVCoordinates.w);
    uv.x = floor(uv.x* Width)/ Width;
    uv.y = floor(uv.y* Height)/ Height;

    uv.x = clamp(uv.x, uCap.x, uCap.y);
    uv.y = clamp(uv.y, vCap.x, vCap.y);
    float n = pattern(Distortion*uv);
    float n2 = pattern(Distortion*uv + 0.01);
    vec2 distortedUV = uv+vec2(cos(n), cos(n));
    vec2 distortedUV2 = uv-vec2(cos(n2), cos(n2));
    vec4 noise = texture(Sampler0, distortedUV)+texture(Sampler0, distortedUV2);
    vec4 color = transformColor(vec4(noise.rgb/2., noise.a), LumiTransparency);
    fragColor = applyFog(color, FogStart, FogEnd, FogColor, vertexDistance);
}