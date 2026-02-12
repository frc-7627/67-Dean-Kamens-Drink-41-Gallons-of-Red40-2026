package frc.bofalib.generic.music;

public final record SongControl<AvailableSong extends Song>(
    AvailableSong song
) {}
