package com.bamplee.chomi.api.datatool.sk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class WeatherHourlyResponse {
    private Weather weather;

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private List<Hourly> hourly;

        public List<Hourly> getHourly() {
            return hourly;
        }

        public void setHourly(List<Hourly> hourly) {
            this.hourly = hourly;
        }

        public static class Hourly {
            private Grid grid;
            private Wind wind;
            private Precipitation precipitation;
            private Sky sky;
            private Temperature temperature;
            private String humidity;
            private String lightning;
            private String sunRiseTime;
            private String sunSetTime;

            public Grid getGrid() {
                return grid;
            }

            public void setGrid(Grid grid) {
                this.grid = grid;
            }

            public Wind getWind() {
                return wind;
            }

            public void setWind(Wind wind) {
                this.wind = wind;
            }

            public Precipitation getPrecipitation() {
                return precipitation;
            }

            public void setPrecipitation(Precipitation precipitation) {
                this.precipitation = precipitation;
            }

            public Sky getSky() {
                return sky;
            }

            public void setSky(Sky sky) {
                this.sky = sky;
            }

            public Temperature getTemperature() {
                return temperature;
            }

            public void setTemperature(Temperature temperature) {
                this.temperature = temperature;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getLightning() {
                return lightning;
            }

            public void setLightning(String lightning) {
                this.lightning = lightning;
            }

            public String getSunRiseTime() {
                return sunRiseTime;
            }

            public void setSunRiseTime(String sunRiseTime) {
                this.sunRiseTime = sunRiseTime;
            }

            public String getSunSetTime() {
                return sunSetTime;
            }

            public void setSunSetTime(String sunSetTime) {
                this.sunSetTime = sunSetTime;
            }

            public static class Grid {
                private String longitude;
                private String latitude;
                private String county;
                private String village;
                private String city;

                public String getLongitude() {
                    return longitude;
                }

                public void setLongitude(String longitude) {
                    this.longitude = longitude;
                }

                public String getLatitude() {
                    return latitude;
                }

                public void setLatitude(String latitude) {
                    this.latitude = latitude;
                }

                public String getCounty() {
                    return county;
                }

                public void setCounty(String county) {
                    this.county = county;
                }

                public String getVillage() {
                    return village;
                }

                public void setVillage(String village) {
                    this.village = village;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }
            }

            public static class Wind {
                private String wdir;
                private String wspd;

                public String getWdir() {
                    return wdir;
                }

                public void setWdir(String wdir) {
                    this.wdir = wdir;
                }

                public String getWspd() {
                    return wspd;
                }

                public void setWspd(String wspd) {
                    this.wspd = wspd;
                }
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Precipitation {
                private String sinceOntime;
                private String type;

                public String getSinceOntime() {
                    return sinceOntime;
                }

                public void setSinceOntime(String sinceOntime) {
                    this.sinceOntime = sinceOntime;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }

            public static class Sky {
                private String code;
                private String name;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class Temperature {
                private String tc;
                private String tmax;
                private String tmin;

                public String getTc() {
                    return tc;
                }

                public void setTc(String tc) {
                    this.tc = tc;
                }

                public String getTmax() {
                    return tmax;
                }

                public void setTmax(String tmax) {
                    this.tmax = tmax;
                }

                public String getTmin() {
                    return tmin;
                }

                public void setTmin(String tmin) {
                    this.tmin = tmin;
                }
            }
        }
    }
}
