const DEFAULT_MODEL = "gemini-3.6-flash";

export default {
  async fetch(request, env) {
    if (request.method !== "POST") {
      return new Response("Method not allowed", { status: 405 });
    }

    let body;

    try {
      body = await request.json();
    } catch {
      return new Response("Invalid JSON", { status: 400 });
    }

    const deviceId = body.deviceId;

    if (!deviceId || typeof deviceId !== "string") {
      return new Response("Missing deviceId", { status: 400 });
    }

    // Per-device rate limit
    const perDevice = await env.PER_DEVICE_LIMITER.limit({
      key: deviceId
    });

    if (!perDevice.success) {
      return new Response(
        "Too many requests — slow down a bit.",
        { status: 429 }
      );
    }

    // Global rate limit
    const global = await env.GLOBAL_LIMITER.limit({
      key: "global"
    });

    if (!global.success) {
      return new Response(
        "The assistant is busy right now — try again shortly.",
        { status: 429 }
      );
    }

    const model = env.MODEL || DEFAULT_MODEL;

    const upstreamResponse = await fetch(
      "https://generativelanguage.googleapis.com/v1beta/openai/chat/completions",
      {
        method: "POST",

        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${env.GEMINI_API_KEY}`
        },

        body: JSON.stringify({
          model,
          messages: body.messages,
          tools: body.tools,
          tool_choice: body.tools ? "auto" : undefined
        })
      }
    );

    const responseText = await upstreamResponse.text();

    return new Response(responseText, {
      status: upstreamResponse.status,
      headers: {
        "Content-Type": "application/json"
      }
    });
  }
};