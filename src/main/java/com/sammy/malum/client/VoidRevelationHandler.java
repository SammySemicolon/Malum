package com.sammy.malum.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sammy.malum.core.handlers.hiding.HiddenTagHandler;
import net.minecraft.client.Minecraft;



import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class VoidRevelationHandler {

	private static final Gson GSON = new GsonBuilder()
		.registerTypeAdapter(RevelationType.class, new RevelationType.Adapter())
		.create();

	private static boolean checkedFile = false;
	private static RevelationType revelationState = RevelationType.NEOPHYTE;

	
	private static Path getPath() {
		return Minecraft.getInstance().gameDirectory.toPath().resolve("malum_extra.json");
	}

	private static void checkFile() {
		if (!checkedFile) {
			try {
				String s = Files.readString(getPath(), StandardCharsets.UTF_8);
				RevelationData data = GSON.fromJson(s, RevelationData.class);
				if (data != null)
					revelationState = data.hasSeenIt;
			} catch (IOException | JsonSyntaxException exception) {
				revelationState = RevelationType.NEOPHYTE;
			}

			checkedFile = true;
		}
	}

	public static boolean hasSeenTheRevelation(RevelationType type) {
		checkFile();
		return revelationState.ordinal() >= type.ordinal();
	}

	public static void seeTheRevelation(RevelationType type) {
		if (!hasSeenTheRevelation(type)) {
			revelationState = type;
			try {
				RevelationData data = new RevelationData();
				data.hasSeenIt = type;

				Files.writeString(getPath(), GSON.toJson(data), StandardCharsets.UTF_8);
			} catch (IOException exception) {
				// NO-OP
			}

			HiddenTagHandler.conditionsChanged();
		}
	}

	public enum RevelationType {
		NEOPHYTE, VOID_READER, BLACK_CRYSTAL;

		private static class Adapter extends TypeAdapter<RevelationType> {
			@Override
			public void write(JsonWriter out, RevelationType value) throws IOException {
				switch (value) {
					case NEOPHYTE -> out.value(false);
					case VOID_READER -> out.value(true);
					case BLACK_CRYSTAL -> out.value("all");
				}
			}

			@Override
			public RevelationType read(JsonReader in) throws IOException {
				JsonToken token = in.peek();
				if (token == JsonToken.BOOLEAN && in.nextBoolean())
					return VOID_READER;
				else if (token == JsonToken.STRING && in.nextString().equals("all"))
					return BLACK_CRYSTAL;
				return NEOPHYTE;
			}
		}
	}

	private static class RevelationData {
		public RevelationType hasSeenIt;
	}
}
