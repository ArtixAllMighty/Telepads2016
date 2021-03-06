package subaraki.telepads.handler;

import java.util.ArrayList;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.server.ServerWorld;

public class CoordinateHandler {

    public static final Random rand = new Random();
    public int worldsize = 25000000;

    int xi = 0;
    int zi = 0;
    String yi = "";
    ResourceLocation dim = null;

    String name = "default";

    public CoordinateHandler(ServerWorld server , String args) {

        String[] s = args.split("/");

        xi = define(s[0]);
        yi = s[1];
        zi = define(s[2]);

        defineDim(server , s[3]);

        this.name = s[4];
    }

    public String getName()
    {

        return name;
    }

    private int define(String definer)
    {

        if (definer.toLowerCase().equals("random"))
        {
            int random = rand.nextInt(worldsize * 2) - worldsize;
            return random;
        }

        else
            if (definer.contains("#"))
            {
                String[] vals = definer.split("#");
                int min = Integer.valueOf(vals[0]);
                int max = Integer.valueOf(vals[1]);

                if (max < min) { throw new IllegalArgumentException(
                        min + "cannot be more then " + max + "! this is an error from the end user in the Telepads configuration file!"); }

                int total = 0;

                if (min < 0 && max >= 0)
                {
                    total = max + (-1 * min);
                }
                else
                    total = max + min;

                int result = rand.nextInt(total);

                if (min < 0 && max >= 0)
                    result += min;

                return result;

            }
            else
                return Integer.valueOf(definer);
    }

    private void defineDim(ServerWorld world, String dimension)
    {

        if (dimension.toLowerCase().equals("random"))
        {
            ArrayList<ResourceLocation> list = Lists.newArrayList();
            // get a random dimension from existing dimensions
            for (ServerWorld dim : world.getServer().getAllLevels())
            {
                list.add(dim.dimension().location());
            }

            ResourceLocation resLoc = list.get(rand.nextInt(list.size()));
            dim = resLoc;

        }

        else
            dim = new ResourceLocation(dimension);
    }

    private int defineY(String definer, World world)
    {

        if (definer.toLowerCase().equals("random"))
        {
            // load chunk ?
            world.getChunk(new BlockPos(xi, 0, zi));
            if (world.getHeight(Type.WORLD_SURFACE, xi, zi) > 0)
            {
                return world.getHeight(Type.WORLD_SURFACE, xi, zi);
            }

            return 0;
        }

        else
            if (definer.contains("#"))
            {
                String[] vals = definer.split("#");
                int min = Integer.valueOf(vals[0]);
                int max = Integer.valueOf(vals[1]);

                if (max < min) { throw new IllegalArgumentException(
                        min + "cannot be more then " + max + "! this is an error from the end user in the Telepads configuration file!"); }

                BlockPos pos = new BlockPos(xi, min, zi);

                int counter = min;

                while (counter < max)
                {
                    counter++;

                    if ((world.getBlockState(pos).isRedstoneConductor(world, pos) && world.isEmptyBlock(pos.above())))
                    {
                        break;
                    }

                    pos = new BlockPos(xi, counter, zi);
                }

                int result = counter;

                // if it reached higher then max, meaning no suitable position was found, get an
                // average of the given height
                if (counter >= max)
                {
                    if (min < 0 && max >= 0)
                    {
                        result = max + (-1 * min);
                    }
                    else
                        result = max + min;

                    result = result / 2 + min;
                }

                return result;

            }
            else
                return Integer.valueOf(definer);
    }

    public BlockPos getPosition(World world)
    {

        int y = defineY(yi, world);
        return new BlockPos(xi, y, zi);
    }

    public ResourceLocation getDimension()
    {

        return dim;
    }
}