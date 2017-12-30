package me.xdrop.jrand.generators.location;

import me.xdrop.jrand.Generator;
import me.xdrop.jrand.annotation.Facade;
import me.xdrop.jrand.data.AssetLoader;
import me.xdrop.jrand.generators.basics.NaturalGenerator;
import me.xdrop.jrand.generators.text.WordGenerator;
import me.xdrop.jrand.model.location.StreetSuffix;
import me.xdrop.jrand.model.location.StreetSuffixMapper;
import me.xdrop.jrand.utils.Choose;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Facade(accessor = "street")
public class StreetGenerator extends Generator<String> {

    private WordGenerator wordGenerator;
    private NaturalGenerator nat;
    private String country;
    private boolean shortSuffix;
    private List<StreetSuffix> ukStreetSuffixes;
    private List<StreetSuffix> usStreetPrefixes;
    private List<StreetSuffix> all;
    private boolean houseNumber;

    public StreetGenerator() {
        this.wordGenerator = new WordGenerator();
        ukStreetSuffixes = AssetLoader.loadList("uk/street_suffixes.txt", new StreetSuffixMapper());
        usStreetPrefixes = AssetLoader.loadList("us/street_suffixes.txt", new StreetSuffixMapper());
        all = new ArrayList<>(ukStreetSuffixes);
        all.addAll(usStreetPrefixes);
        this.country = "all";
        this.nat = new NaturalGenerator();
    }

    /**
     * Use street suffixes from the United States
     *
     * @return The same generator
     */
    public StreetGenerator us() {
        this.country = "us";
        return this;
    }


    /**
     * Use street suffixes from the United Kingdom
     *
     * @return The same generator
     */
    public StreetGenerator uk() {
        this.country = "uk";
        return this;
    }

    /**
     * Set whether the street suffix to be appended is in
     * it's short version
     *
     * @param enable True for short suffix,
     *               False otherwise
     * @return The same generator
     */
    public StreetGenerator shortSuffix(boolean enable) {
        this.shortSuffix = enable;
        return this;
    }

    /**
     * Set whether the street suffix to be appended is in
     * it's short version
     *
     * @return The same generator
     */
    public StreetGenerator shortSuffix() {
        return shortSuffix(true);
    }

    /**
     * Append a house number to the front of the street
     *
     * @return The same generator
     */
    public StreetGenerator houseNumber() {
        return houseNumber(true);
    }

    /**
     * Append a house number to the front of the street
     * @param enable True to append,
     *               False to not append
     * @return The same generator
     */
    public StreetGenerator houseNumber(boolean enable) {
        this.houseNumber = enable;
        return this;
    }

    @Override
    public String gen() {
        String road = "";

        if (houseNumber) {
            road += nat.range(1,200).gen() + " ";
        }

        road += wordGenerator.capitalize().gen() + " ";
        StreetSuffix suffix;

        if (country.equals("uk")) {
            suffix = Choose.chooseOne(ukStreetSuffixes);
        } else if (country.equals("us")) {
            suffix = Choose.chooseOne(usStreetPrefixes);
        } else {
            suffix = Choose.chooseOne(all);
        }

        if (shortSuffix) {
            road += suffix.getShortVersion();
        } else {
            road += suffix.getLongVersion();
        }

        return road;
    }
    
    @Generated("me.xdrop.jrand.annotation.processing.ForkClassGenerator")
    public final StreetGenerator fork() {
        return new StreetGenerator(
                wordGenerator.fork(),
                nat.fork(),
                country,
                shortSuffix,
                new java.util.ArrayList<>(ukStreetSuffixes),
                new java.util.ArrayList<>(usStreetPrefixes),
                new java.util.ArrayList<>(all),
                houseNumber);
    }
    
    @Generated("me.xdrop.jrand.annotation.processing.ForkClassGenerator")
    private StreetGenerator(WordGenerator wordGenerator, NaturalGenerator nat, String country,
            boolean shortSuffix, List<StreetSuffix> ukStreetSuffixes,
            List<StreetSuffix> usStreetPrefixes, List<StreetSuffix> all, boolean houseNumber) {
        this.wordGenerator = wordGenerator;
        this.nat = nat;
        this.country = country;
        this.shortSuffix = shortSuffix;
        this.ukStreetSuffixes = ukStreetSuffixes;
        this.usStreetPrefixes = usStreetPrefixes;
        this.all = all;
        this.houseNumber = houseNumber;
    }
}